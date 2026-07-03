package Service;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import Model.User;

public class SocialAuthService {

	public User loginWithGoogle() throws Exception {
		// Load credentials
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(GsonFactory.getDefaultInstance(),
				new InputStreamReader(new FileInputStream("resources/credentials.json")));

		// OAuth Flow
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance(), clientSecrets,
				List.of("openid", "email", "profile")).setAccessType("offline").build();

		// Local callback server
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

		// Login Google
		Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

		// Access Token
		String accessToken = credential.getAccessToken();

		// Gọi Google UserInfo API
		HttpClient client = HttpClient.newHttpClient();

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://www.googleapis.com/oauth2/v2/userinfo"))
				.header("Authorization", "Bearer " + accessToken).GET().build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		// Parse JSON
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(response.body(), JsonObject.class);

		// Tạo User
		User user = new User();

		String fbName = json.get("name").getAsString();
		String[] parts = fbName.trim().split("\\s+");
		String fullName = "";

		for (int i = 1; i < parts.length; i++) {
			fullName += parts[i];
		}
		fullName += parts[0];
		String name = parts[0];

		user.setUsername(name);
		user.setFullName(fullName);
		user.setEmail(json.get("email").getAsString());
		user.setProvider("GOOGLE");
		user.setProviderId(json.get("id").getAsString());
		user.setStatus(true);

		return user;
	}
}