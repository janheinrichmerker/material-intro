package com.heinrichreimersoftware.materialintro.demo;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.heinrichreimersoftware.materialintro.app.SlideFragment;
import com.heinrichreimersoftware.materialintro.demo.databinding.FragmentLoginBinding;

public class LoginFragment extends SlideFragment {

    private FragmentLoginBinding binding;

    private boolean loggedIn = false;
    private Handler loginHandler = new Handler();
    private Runnable loginRunnable = new Runnable() {
        @Override
        public void run() {
            binding.fakeLogin.setText(R.string.label_fake_login_success);
            Toast.makeText(getContext(), R.string.label_fake_login_success_message, Toast.LENGTH_SHORT).show();

            loggedIn = true;
            updateNavigation();
        }
    };

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        binding.fakeUsername.setEnabled(!loggedIn);
        binding.fakePassword.setEnabled(!loggedIn);
        binding.fakeLogin.setEnabled(!loggedIn);

        binding.fakeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fakeUsername.setEnabled(false);
                binding.fakePassword.setEnabled(false);
                binding.fakeLogin.setEnabled(false);
                binding.fakeLogin.setText(R.string.label_fake_login_loading);

                loginHandler.postDelayed(loginRunnable, 2000);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        loginHandler.removeCallbacks(loginRunnable);
        super.onDestroy();
    }

    @Override
    public boolean canGoForward() {
        return loggedIn;
    }
}
