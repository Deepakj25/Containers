package com.example.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;

@Controller
@RequestMapping
public class AuthController {

    private final InMemoryLoginStore store;

    public AuthController(InMemoryLoginStore store) {
        this.store = store;
    }

    @GetMapping("/")
    public String root(HttpSession session) {
        return session.getAttribute("userEmail") == null ? "redirect:/login" : "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("form", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@Valid @ModelAttribute("form") LoginForm form,
                          BindingResult binding,
                          HttpServletRequest request,
                          HttpSession session,
                          Model model) {
        if (binding.hasErrors()) {
            return "login";
        }
        String ip = extractClientIp(request);
        String ua = request.getHeader("User-Agent");
        store.add(new LoginRecord(form.getEmail(), form.getPassword(), Instant.now(), ip, ua));
        session.setAttribute("userEmail", form.getEmail());
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Object userEmail = session.getAttribute("userEmail");
        if (userEmail == null) return "redirect:/login";
        model.addAttribute("currentUser", userEmail.toString());
        model.addAttribute("myLogins", store.getByEmail(userEmail.toString()));
        model.addAttribute("allLogins", store.getAll());
        return "dashboard";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    private static String extractClientIp(HttpServletRequest req) {
        String xff = req.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            return xff.split(",")[0].trim();
        }
        return req.getRemoteAddr();
    }
}
