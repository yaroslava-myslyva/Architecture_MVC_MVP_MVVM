package com.example.architecture_mvc_mvp_mvvm.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.architecture_mvc_mvp_mvvm.R

import com.example.architecture_mvc_mvp_mvvm.viewmodels.AuthViewModel
import com.example.architecture_mvc_mvp_mvvm.viewmodels.LoginState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() { // Екстендив LoginView

    private val viewModel = AuthViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            viewModel.login(email = view.findViewById<EditText>(R.id.textEmail).text.toString(),
            password = view.findViewById<EditText>(R.id.textPassword).text.toString())
        }
        viewModel.state.observe(viewLifecycleOwner,
        Observer<LoginState> { state ->
            when(state){
                is LoginState.LoginSucceededState -> {
                    Toast.makeText(requireContext(), "Success login", Toast.LENGTH_SHORT).show()
                }
                is LoginState.SendingState -> {
                    view.findViewById<EditText>(R.id.textEmail).isEnabled = false
                    view.findViewById<EditText>(R.id.textPassword).isEnabled = false
                    btnLogin.isEnabled = false
                }
                is LoginState.ErrorState<*> -> {
                    when(state.message){
                        is Int -> Toast.makeText(requireContext(), getString(state.message), Toast.LENGTH_SHORT).show()
                        is String -> Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                    view.findViewById<EditText>(R.id.textEmail).isEnabled = true
                    view.findViewById<EditText>(R.id.textPassword).isEnabled = true
                    btnLogin.isEnabled = true
                }
                is LoginState.DefaultState -> {
                    view.findViewById<EditText>(R.id.textEmail).isEnabled = true
                    view.findViewById<EditText>(R.id.textPassword).isEnabled = true
                    btnLogin.isEnabled = true
                }
            }
        })
    }
}
