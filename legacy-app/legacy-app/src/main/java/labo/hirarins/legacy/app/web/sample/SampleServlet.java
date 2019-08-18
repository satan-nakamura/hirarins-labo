package labo.hirarins.legacy.app.web.sample;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import labo.hirarins.legacy.app.UserDaoRmi.User;
import labo.hirarins.legacy.app.web.Initializer;
import labo.hirarins.legacy.app.web.sample.LoginService.InvalidLoginException;
import labo.hirarins.legacy.app.web.sample.LoginService.UserLockedException;


public class SampleServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(SampleServlet.class.getName());

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        new Initializer().init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        log.info("SampleServlet.doGet.");
        req.getRequestDispatcher("/index.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        log.info("SampleServlet.doPost");
        UserForm userForm = new UserForm(req.getParameter("login.userId"), req.getParameter("login.passcode"));
        log.info("userForm=" + userForm);

        try {
            LoginService loginService = new LoginService();
            User user = loginService.login(userForm);
            req.getSession(true).setAttribute(User.SESSION_KEY, user);
            req.getRequestDispatcher("/result.jsp").forward(req, res);
        } catch (InvalidLoginException e) {
            req.getSession(true).invalidate();
            req.getRequestDispatcher("/index.jsp").forward(req, res);
        } catch (UserLockedException e) {
            req.getSession(true).invalidate();
            req.getRequestDispatcher("/locked.jsp").forward(req, res);
		}
    }
    
    
}