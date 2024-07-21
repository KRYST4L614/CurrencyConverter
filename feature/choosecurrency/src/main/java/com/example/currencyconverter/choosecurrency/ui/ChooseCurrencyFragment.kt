package com.example.currencyconverter.choosecurrency.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.choosecurrency.di.DaggerChooseCurrencyComponent
import com.example.currencyconverter.choosecurrency.presentation.ChooseCurrencyViewModel
import com.example.currencyconverter.choosecurrency.presentation.UIState
import com.example.currencyconverter.feature.choosecurrency.R
import com.example.currencyconverter.feature.choosecurrency.databinding.FragmentChooseCurrencyBinding
import com.example.currencyconverter.shared.animations.bounce
import com.example.currencyconverter.shared.fragmentdependencies.FragmentDependenciesStore
import javax.inject.Inject

class ChooseCurrencyFragment : Fragment() {

    companion object {
        fun newInstance() = ChooseCurrencyFragment()
    }

    private var _binding: FragmentChooseCurrencyBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ChooseCurrencyViewModel> { viewModelFactory }

    private var savedInstanceState: Bundle? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerChooseCurrencyComponent
            .builder()
            .dependencies(FragmentDependenciesStore.dependencies)
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseCurrencyBinding.inflate(layoutInflater)
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
                is UIState.Content -> observeContentState(it)
                is UIState.Loading -> observeLoadingState()
                is UIState.Error -> observeErrorState(it)
            }
        }
    }

    private fun observeContentState(content: UIState.Content) {
        with(binding) {
            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.currency_item,
                R.id.currency,
                content.currencies
            )

            fromCurrency.setAdapter(adapter)
            toCurrency.setAdapter(adapter)

            if (fromCurrency.text.isNullOrEmpty()) {
                toCurrency.setText(content.currencies[0], false)
                fromCurrency.setText(content.currencies[0], false)
            }

            progress.isVisible = false
            contentGroup.isVisible = true

            amountLayout.error = content.amountError
            amountLayout.isErrorEnabled = content.amountError != null
        }
    }

    private fun observeLoadingState() {
        with(binding) {
            error.root.isVisible = false
            contentGroup.isVisible = false
            progress.isVisible = true
        }
    }

    private fun observeErrorState(errorState: UIState.Error) {
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
                val fromCurrencyText = fromCurrency.text
                fromCurrency.setText(toCurrency.text, false)
                toCurrency.setText(fromCurrencyText, false)
            }

            convertButton.setOnClickListener {
                viewModel.handleConvert(
                    amount.text?.toString(),
                    fromCurrency.text.toString(),
                    toCurrency.text.toString()
                )

                if (amountLayout.error != null) {
                    bounce(amountLayout, 0f, 2_000f)
                }
            }

            error.retryButton.setOnClickListener {
                viewModel.getCurrencies()
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