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
import com.example.architecture_mvc_mvp_mvvm.presenter.LoginPresenter
import com.example.architecture_mvc_mvp_mvvm.presenter.LoginPresenterImpl
import com.example.architecture_mvc_mvp_mvvm.presenter.LoginView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SettingsFragment : Fragment(), LoginView {

    private val loginPresenter = LoginPresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginPresenter.attachView(view = this@SettingsFragment)
        // Це та інше реалізоване в біблотеці Moxy
    }

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
            loginPresenter.login(
                email = view.findViewById<EditText>(R.id.textEmail)?.editableText.toString(),
                password = view.findViewById<EditText>(R.id.textPassword)?.editableText.toString()
            )
        }
    }

    override fun showSuccess() {
        Toast.makeText(requireContext(), "Success login", Toast.LENGTH_SHORT).show()
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun showError(message: Int) {
        Toast.makeText(requireContext(), getString(message), Toast.LENGTH_SHORT).show()
    }
}