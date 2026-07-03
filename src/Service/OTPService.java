package Service;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Random;

import Dao.OTPDao;
import Model.OTP;
import Model.User;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class OTPService {
	
	private OTPDao otpd = new OTPDao();

	public boolean sendOTP(User user) {
		if (user != null && user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
		    Random random = new Random();
		    String otp = String.format("%06d", random.nextInt(1000000));
			
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");

			Session session = Session.getInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("23130288@st.hcmuaf.edu.vn", "wacswtfmfirfnzxj");
				}
			});

			Message message = new MimeMessage(session);
			try {
				message.setFrom(new InternetAddress("23130288@st.hcmuaf.edu.vn"));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
				message.setSubject("OTP Verification");
				message.setText("Your OTP is: " + otp);
				Transport.send(message);
				
				otpd.saveOTP(user.getId(), otp);
				return true;
			} catch (MessagingException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	public boolean verifyOTP(User user, String otp) {
		if (user != null && user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
			OTP otpClass = otpd.getOTPByUid(user.getId());
			if (otpClass == null)
				return false;
			return otpClass.getOtp().equals(otp) && LocalDateTime.now().isBefore(otpClass.getExpiredTime());
		}
		return false;
	}

	public void deleteOTP(User user) {
		otpd.deleteOTP(user.getId());
	}
}
