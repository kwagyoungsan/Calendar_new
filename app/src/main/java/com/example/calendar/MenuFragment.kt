package com.example.calendar

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val binding = FragmentMenuBinding.inflate(inflater, container, false)

        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Toast.makeText(context, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
            } else if (tokenInfo != null) {
                Toast.makeText(context, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
                Log.i(
                    tag, "토큰 정보 보기 성공" + "\n회원번호 : ${tokenInfo.id}" +
                            "\n만료시간: ${tokenInfo.expiresIn} 초"
                )
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
                        Toast.makeText(mainActivity,"설정이 올바르지 않음(android key hash)",Toast.LENGTH_SHORT).show()
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

        UserApiClient.instance.me { user, error ->
            if (error != null) { // 로그아웃 상태
                binding.kakaoLoginbt.setVisibility(View.VISIBLE)
                binding.kakaoLogoutBt.setVisibility(View.GONE)
                binding.kakaoUnlinkBt.setVisibility(View.GONE)
            } else if (user != null) { // 로그인 상태
                binding.kakaoLoginbt.setVisibility(View.GONE)
                binding.kakaoLogoutBt.setVisibility(View.VISIBLE)
                binding.kakaoUnlinkBt.setVisibility(View.VISIBLE)
            }
        }

        UserApiClient.instance.me{user, error->
            binding.kakaoInformation.text="\n이메일: ${user?.kakaoAccount?.email}"+"\n닉네임: ${user?.kakaoAccount?.profile?.nickname}"


            Glide.with(this)
                .load(user?.kakaoAccount?.profile?.thumbnailImageUrl)
                .into(binding.kakaoProfile)
        }



        binding.kakaoLoginbt.setOnClickListener {
            if (LoginClient.instance.isKakaoTalkLoginAvailable(mainActivity)) {
                LoginClient.instance.loginWithKakaoTalk(mainActivity, callback = callback)
            } else {
                LoginClient.instance.loginWithKakaoAccount(mainActivity, callback = callback)
            }
        }

        binding.calendarbtMenu.setOnClickListener {
            activity?.let{
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
            }
        }

        binding.planlistMenu.setOnClickListener {
            activity?.let{
                val intent = Intent(context, ListActivity::class.java)
                startActivity(intent)
            }
        }

        binding.kakaoLogoutBt.setOnClickListener {
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Toast.makeText(mainActivity, "로그아웃 실패 $error", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(mainActivity, "로그아웃 성공", Toast.LENGTH_SHORT).show()
                }
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
                System.exit(0)
            }
        }



        binding.kakaoUnlinkBt.setOnClickListener {
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Toast.makeText(mainActivity, "회원 탈퇴 실패 $error", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(mainActivity, "회원 탈퇴 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
                    System.exit(0)
                }
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