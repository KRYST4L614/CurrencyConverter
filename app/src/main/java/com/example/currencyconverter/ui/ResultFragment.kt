package com.example.currencyconverter.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.App
import com.example.currencyconverter.databinding.FragmentResultBinding
import com.example.currencyconverter.presentation.resultfragment.ResultState
import com.example.currencyconverter.presentation.resultfragment.ResultViewModel
import javax.inject.Inject

class ResultFragment : Fragment() {

    companion object {
        private const val VALUE_KEY = "VALUE"
        private const val CURRENCY_FROM_KEY = "CURRENCY_FROM"
        private const val CURRENCY_TO_KEY = "CURRENCY_TO"

        fun newInstance(value: Double, currencyFrom: String, currencyTo: String): ResultFragment {
            val fragment = ResultFragment()
            val args = Bundle().apply {
                putDouble(VALUE_KEY, value)
                putString(CURRENCY_FROM_KEY, currencyFrom)
                putString(CURRENCY_TO_KEY, currencyTo)
            }
            return fragment.apply {
                arguments = args
            }
        }
    }

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ResultViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModelStateObserver()

        setClickListeners()

        convert()
    }

    private fun setViewModelStateObserver() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Content -> observeContentState(it)
                is ResultState.Loading -> observeLoadingState()
                is ResultState.Error -> observeErrorState(it)
            }
        }
    }

    private fun observeContentState(content: ResultState.Content) {
        with(binding) {
            contentGroup.isVisible = true
            result.text = content.result
            progress.isVisible = false
        }
    }

    private fun observeLoadingState() {
        with(binding) {
            error.root.isVisible = false
            contentGroup.isVisible = false
            progress.isVisible = true
        }
    }

    private fun observeErrorState(errorState: ResultState.Error) {
        with(binding) {
            contentGroup.isVisible = false
            progress.isVisible = false
            error.root.isVisible = true
            error.errorMessage.text = errorState.errorMessage
        }
    }

    private fun setClickListeners() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                viewModel.close()
            }

            backButton.setOnClickListener {
                viewModel.close()
            }

            error.retryButton.setOnClickListener {
                convert()
            }
        }
    }

    private fun convert() {
        with(requireArguments()) {
            viewModel.convertCurrencies(
                getDouble(VALUE_KEY), getString(
                    CURRENCY_FROM_KEY
                )!!, getString(CURRENCY_TO_KEY)!!
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}