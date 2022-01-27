package com.tigerlogistics.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tigerlogistics.appuser.AppUserRole;
import com.tigerlogistics.email.EmailSender;
import com.tigerlogistics.email.EmailValidator;
import com.tigerlogistics.model.AppUser;
import com.tigerlogistics.model.ConfirmationToken;
import com.tigerlogistics.repository.AppUserRepository;
import com.tigerlogistics.request.RegistrationRequest;

@Service
public class AppUserService implements UserDetailsService {

	private static final String USER_NOT_FOUND_MSG="user with email %s not found";
	private final AppUserRepository appUserRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ConfirmationTokenService confirmationTokenService;
	private final EmailValidator emailValidator;
	private final AppUserService appUserService;
	private final EmailSender emailSender;
	
	

	

	

	public AppUserService(AppUserRepository appUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
			ConfirmationTokenService confirmationTokenService, EmailValidator emailValidator,
			AppUserService appUserService, EmailSender emailSender) {
		super();
		this.appUserRepository = appUserRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.confirmationTokenService = confirmationTokenService;
		this.emailValidator = emailValidator;
		this.appUserService = appUserService;
		this.emailSender = emailSender;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
	}

	public String signUpUser(AppUser appUser) {
		// TODO Auto-generated method stub
		boolean userExist = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
		if(userExist) {
			throw new IllegalStateException("user already exist");
		}
		String encodedPassword=  bCryptPasswordEncoder.encode(appUser.getPassword());
		appUser.setPassword(encodedPassword);
		appUserRepository.save(appUser);
		String token = UUID.randomUUID().toString();
		ConfirmationToken confirmationToken = new ConfirmationToken(token,LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(15),appUser);
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		return null;
	}
//	public String register(RegistrationRequest request) {
//        boolean isValidEmail = emailValidator.
//                test(request.getEmail());
//
//        if (!isValidEmail) {
//            throw new IllegalStateException("email not valid");
//        }
//
//        String token = appUserService.signUpUser(
//                new AppUser(
//                        request.getFirstName(),
//                        request.getLastName(),
//                        request.getEmail(),
//                        request.getPassword(),
//                        AppUserRole.USER
//
//                )
//        );
//
//        String link = "http://localhost:8090/api/v1/registration/confirm?token=" + token;
//        emailSender.send(
//                request.getEmail(),
//                buildEmail(request.getFirstName(), link));
//
//        return token;
//    }

	private String buildEmail(String name, String link) {

        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +

                "\n" +

                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +

                "\n" +

                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +

                "    <tbody><tr>\n" +

                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +

                "        \n" +

                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +

                "          <tbody><tr>\n" +

                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +

                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +

                "                  <tbody><tr>\n" +

                "                    <td style=\"padding-left:10px\">\n" +

                "                  \n" +

                "                    </td>\n" +

                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +

                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +

                "                    </td>\n" +

                "                  </tr>\n" +

                "                </tbody></table>\n" +

                "              </a>\n" +

                "            </td>\n" +

                "          </tr>\n" +

                "        </tbody></table>\n" +

                "        \n" +

                "      </td>\n" +

                "    </tr>\n" +

                "  </tbody></table>\n" +

                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +

                "    <tbody><tr>\n" +

                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +

                "      <td>\n" +

                "        \n" +

                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +

                "                  <tbody><tr>\n" +

                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +

                "                  </tr>\n" +

                "                </tbody></table>\n" +

                "        \n" +

                "      </td>\n" +

                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +

                "    </tr>\n" +

                "  </tbody></table>\n" +

                "\n" +

                "\n" +

                "\n" +

                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +

                "    <tbody><tr>\n" +

                "      <td height=\"30\"><br></td>\n" +

                "    </tr>\n" +

                "    <tr>\n" +

                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +

                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +

                "        \n" +

                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +

                "        \n" +

                "      </td>\n" +

                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +

                "    </tr>\n" +

                "    <tr>\n" +

                "      <td height=\"30\"><br></td>\n" +

                "    </tr>\n" +

                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +

                "\n" +

                "</div></div>";

    }


}
