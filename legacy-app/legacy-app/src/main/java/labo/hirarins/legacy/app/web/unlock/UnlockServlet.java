package labo.hirarins.legacy.app.web.unlock;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import labo.hirarins.legacy.app.UserDaoRmi.User;
import labo.hirarins.legacy.app.web.unlock.UnlockService.InvalidUnlockRequestException;

public class UnlockServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(UnlockServlet.class.getName());

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        log.info("UnlockServlet.doPost");
        UnlockForm unlockForm = new UnlockForm(
            req.getParameter("unlock.userId"),
            req.getParameter("unlock.passcode")
        );

        UnlockService unlockService = new UnlockService();
        try {
            User user = unlockService.requestUnlock(unlockForm);
            req.getSession(true).setAttribute(User.SESSION_KEY, user);
            req.getRequestDispatcher("/result.jsp").forward(req, res);
            
        } catch (InvalidUnlockRequestException e) {
            req.getSession(true).invalidate();
            req.getRequestDispatcher("/locked.jsp").forward(req, res);
		}

    }
    
}