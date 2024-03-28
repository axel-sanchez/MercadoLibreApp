package com.example.mercadolibreapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mercadolibreapp.R
import com.example.mercadolibreapp.databinding.FragmentHomeBinding
import com.example.mercadolibreapp.helpers.Constants.QUERY
import com.example.mercadolibreapp.helpers.hide
import com.example.mercadolibreapp.helpers.show
import com.example.mercadolibreapp.presentation.viewmodel.HomeViewModel

/**
 * @author Axel Sanchez
 */
class HomeFragment : Fragment() {
    private var fragmentHomeBinding: FragmentHomeBinding? = null
    private val binding get() = fragmentHomeBinding!!
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentHomeBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAuth()
        requestAuth()

        binding.btnLogin.setOnClickListener {
            requestAuth()
        }

        binding.etSearch.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (binding.etSearch.text.isNullOrEmpty()) Toast.makeText(
                context,
                "Debe ingresar su búsqueda",
                Toast.LENGTH_SHORT
            ).show()
            else {
                if (id == EditorInfo.IME_ACTION_SEARCH || id == EditorInfo.IME_NULL) {

                    val bundle = bundleOf(
                        QUERY to binding.etSearch.text.toString()
                    )
                    findNavController().navigate(R.id.action_homeFragment_to_searchFragment, bundle)

                    return@OnEditorActionListener true
                }
            }
            false
        })
    }

    private fun requestAuth(){
        authenticate { auth ->
            if (auth) {
                binding.btnLogin.hide()
                binding.etSearch.show()
            } else {
                binding.btnLogin.show()
                binding.etSearch.hide()
            }
        }
    }

    private fun setUpAuth() {
        if (BiometricManager.from(requireContext())
                .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS
        ) {
            viewModel.setAuth(true)
            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticación Biométrica")
                .setSubtitle("Autentícate utilizando el sensor biométrico")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build()
        }
    }

    private fun authenticate(auth: (auth: Boolean) -> Unit) {
        if (viewModel.getAuth()) {
            BiometricPrompt(this, ContextCompat.getMainExecutor(requireContext()), object :
                BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    auth(true)
                }
            }).authenticate(promptInfo)
        } else auth(true)
    }
}