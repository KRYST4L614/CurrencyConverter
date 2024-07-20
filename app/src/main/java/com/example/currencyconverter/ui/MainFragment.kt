package com.example.currencyconverter.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.App
import com.example.currencyconverter.R
import com.example.currencyconverter.databinding.FragmentMainBinding
import com.example.currencyconverter.presentation.mainfragment.MainState
import com.example.currencyconverter.presentation.mainfragment.MainViewModel
import com.example.currencyconverter.util.bounce
import javax.inject.Inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    private var savedInstanceState: Bundle? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!viewModel.state.isInitialized) {
            viewModel.getCurrencies()
        }

        this.savedInstanceState = savedInstanceState

        setViewModelStateObserver()

        setClickListeners()
    }

    private fun setViewModelStateObserver() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is MainState.Content -> observeContentState(it)
                is MainState.Loading -> observeLoadingState()
                is MainState.Error -> observeErrorState(it)
            }
        }
    }

    private fun observeContentState(content: MainState.Content) {
        with(binding) {
            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.currency_item,
                R.id.currency,
                content.currencies
            )
            fromCurrency.adapter = adapter
            toCurrency.adapter = adapter
            progress.isVisible = false
            contentGroup.isVisible = true

            fromCurrency.setSelection(content.fromCurrencyPos)
            toCurrency.setSelection(content.toCurrencyPos)

            amountLayout.error = content.amountError
        }
    }

    private fun observeLoadingState() {
        with(binding) {
            error.root.isVisible = false
            contentGroup.isVisible = false
            progress.isVisible = true
        }
    }

    private fun observeErrorState(errorState: MainState.Error) {
        with(binding) {
            contentGroup.isVisible = false
            progress.isVisible = false
            error.root.isVisible = true
            error.errorMessage.text = errorState.errorMessage
        }
    }

    private fun setClickListeners() {
        with(binding) {
            swapButton.setOnClickListener {
                val fromCurrencyPos = fromCurrency.selectedItemPosition
                fromCurrency.setSelection(toCurrency.selectedItemPosition)
                toCurrency.setSelection(fromCurrencyPos)
            }

            convertButton.setOnClickListener {
                viewModel.handleConvert(
                    amount.text?.toString(),
                    fromCurrency.selectedItem.toString(),
                    toCurrency.selectedItem.toString()
                )

                if (amountLayout.error != null) {
                    bounce(amountLayout, 0f, 2_000f)
                }
            }

            error.retryButton.setOnClickListener {
                viewModel.getCurrencies()
            }

            fromCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.handleChooseCurrency(position, toCurrency.selectedItemPosition)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }

            toCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.handleChooseCurrency(fromCurrency.selectedItemPosition, position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }

            amount.doAfterTextChanged {
                viewModel.handleAmountChange(amount.text?.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}