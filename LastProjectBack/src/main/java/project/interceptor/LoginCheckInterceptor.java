package project.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;


public class LoginCheckInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		// 요청 주소에 /board가 포함된 경우, 세션에 user 정보가 존재하는지 체크	
		// 포함되지 않은 경우 메시지와 함께 login 페이지로 이동
		// <<커스터마이징 수정필요>>
		
		if (request.getRequestURI().indexOf("/something") >= 0 && request.getSession().getAttribute("user") == null) {
			request.getSession().setAttribute("message", "로그인 후 사용하실 수 있습니다.");
			response.sendRedirect("/login");
			return false;
		} else {
			return true;
		}
	}
}
