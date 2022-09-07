package com.example.architecture_mvc_mvp_mvvm.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.architecture_mvc_mvp_mvvm.R
import com.example.architecture_mvc_mvp_mvvm.domain.implementations.AuthRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    private var authRepository = AuthRepositoryImpl()

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
            if (!validateEmail()) return@setOnClickListener
            if(!validatePassword()) return@setOnClickListener
            performLogin() // Тут автор у відео трохи помилився, бо у разі помилки не вискакує нічого. Але зараз не суть. І взагалі я накостилила. Але не важливо
        }

    }

    // Model implementation
    private fun performLogin() {
        CoroutineScope(Dispatchers.IO).async {
            val errorMessage = authRepository.login(
                email = view?.findViewById<EditText>(R.id.textEmail)?.editableText.toString(),
                password = view?.findViewById<EditText>(R.id.textPassword)?.editableText.toString()
            ).await()

            if (errorMessage.isEmpty()) {
                launch(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Success login", Toast.LENGTH_SHORT).show()
                }
            } else {
                launch(Dispatchers.Main) {
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateEmail(): Boolean{
        Toast.makeText(requireContext(), view?.findViewById<EditText>(R.id.textEmail)?.editableText.toString(), Toast.LENGTH_SHORT).show()
        return view?.findViewById<EditText>(R.id.textEmail)?.editableText.toString().contains("@")
                && view?.findViewById<EditText>(R.id.textEmail)?.editableText.toString().contains(".")
    }

    private fun validatePassword(): Boolean{
        return view?.findViewById<EditText>(R.id.textPassword)?.editableText.toString().length > 8
    }
}