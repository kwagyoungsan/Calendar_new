package com.example.calendar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.MainThread
import com.example.calendar.databinding.FragmentMenuBinding
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause.*
import com.kakao.sdk.user.UserApiClient


class MenuFragment : Fragment() {
    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMenuBinding.inflate(inflater, container, false)
//        binding.textView.text = arguments?.getString("Key")

        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Toast.makeText(context, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
            } else if (tokenInfo != null) {
                Toast.makeText(context, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                System.exit(0)
            }
        }

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AccessDenied.toString() -> {
                        Toast.makeText(mainActivity, "접근이 거부됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidClient.toString() -> {
                        Toast.makeText(mainActivity, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidGrant.toString() -> {
                        Toast.makeText(
                            mainActivity,
                            "인증 수단이 유효하지 않아 인증할 수 없는 상태",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    error.toString() == InvalidRequest.toString() -> {
                        Toast.makeText(mainActivity, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidScope.toString() -> {
                        Toast.makeText(mainActivity, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == Misconfigured.toString() -> {
                        Toast.makeText(
                            mainActivity,
                            "설정이 올바르지 않음(android key hash)",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    error.toString() == ServerError.toString() -> {
                        Toast.makeText(mainActivity, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == Unauthorized.toString() -> {
                        Toast.makeText(mainActivity, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(mainActivity, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            } else if (token != null) {
                Toast.makeText(mainActivity, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                activity?.let {
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        binding.kakaoLoginbt.setOnClickListener {
            if (LoginClient.instance.isKakaoTalkLoginAvailable(mainActivity)) {
                LoginClient.instance.loginWithKakaoTalk(mainActivity, callback = callback)
            } else {
                LoginClient.instance.loginWithKakaoAccount(mainActivity, callback = callback)
            }
        }

        return binding.root

    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}