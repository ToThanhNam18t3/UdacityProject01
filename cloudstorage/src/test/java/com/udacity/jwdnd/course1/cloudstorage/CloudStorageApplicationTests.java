package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	/**
	 * Adding note test case
	 */
	@Test
	public void addNote_then_doNothing() {
		// Create a test count
		doMockSignUp("Test", "Still Test", "NamTT20", "NamTT20");
		doLogIn("NamTT20", "NamTT20");

		// Upload a note
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
		noteTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-note-btn")));
		WebElement addNewNoteBtn = driver.findElement(By.id("add-note-btn"));
		addNewNoteBtn.click();

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("noteModal")));
		WebElement noteTitleTxt = driver.findElement(By.id("note-title"));
		noteTitleTxt.sendKeys("Adding note title");

		WebElement noteDescriptionTxt = driver.findElement(By.id("note-description"));
		noteDescriptionTxt.sendKeys("Adding note description");

		WebElement saveChangesBtn = driver.findElement(By.id("note-save-button"));
		saveChangesBtn.click();

		Assertions.assertTrue(driver.getPageSource().contains("Adding note title"));
	}

	/**
	 * Updating note test case
	 */
	@Test
	public void updateNote_then_doNothing(){
		// Create a test count
		doMockSignUp("Test", "Test", "NamTT20", "NamTT20");
		doLogIn("NamTT20", "NamTT20");

		// Upload a note
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
		noteTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-note-btn")));
		WebElement addNewNoteBtn = driver.findElement(By.id("add-note-btn"));
		addNewNoteBtn.click();

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("noteModal")));
		WebElement noteTitleTxt = driver.findElement(By.id("note-title"));
		noteTitleTxt.sendKeys("Test add note title");

		WebElement noteDescriptionTxt = driver.findElement(By.id("note-description"));
		noteDescriptionTxt.sendKeys("Test add note description");

		WebElement saveChangesBtn = driver.findElement(By.id("note-save-button"));
		saveChangesBtn.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("view-note-btn")));
		WebElement viewNoteBtn = driver.findElement(By.id("view-note-btn"));
		viewNoteBtn.click();

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("noteModal")));
		noteTitleTxt = driver.findElement(By.id("note-title"));
		noteTitleTxt.clear();
		noteTitleTxt.sendKeys("Edit note title");

		noteDescriptionTxt = driver.findElement(By.id("note-description"));
		noteDescriptionTxt.clear();
		noteDescriptionTxt.sendKeys("Test add note description Editted");

		saveChangesBtn = driver.findElement(By.id("note-save-button"));
		saveChangesBtn.click();

		Assertions.assertTrue(driver.getPageSource().contains("Edit note title"));
	}

	/**
	 * Deleting note test case
	 */
	@Test
	public void deleteNote_then_doNothing() {
		// Create a test count
		doMockSignUp("Test", "Test", "NamTT20", "NamTT20");
		doLogIn("NamTT20", "NamTT20");

		// Upload a note
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
		noteTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-note-btn")));
		WebElement addNewNoteBtn = driver.findElement(By.id("add-note-btn"));
		addNewNoteBtn.click();

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("noteModal")));
		WebElement noteTitleTxt = driver.findElement(By.id("note-title"));
		noteTitleTxt.sendKeys("Adding note title");

		WebElement noteDescriptionTxt = driver.findElement(By.id("note-description"));
		noteDescriptionTxt.sendKeys("Test add note description");

		WebElement saveChangesBtn = driver.findElement(By.id("note-save-button"));
		saveChangesBtn.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("view-note-btn")));
		WebElement deleteBtn = driver.findElement(By.id("delete-note-button"));
		deleteBtn.click();

		webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("view-note-btn")));
		Assertions.assertFalse(driver.getPageSource().contains("Adding note title"));
	}

	/**
	 * Uploading file test case
	 */
	@Test
	public void uploadFile_then_doNothing() {
		// Create a test count
		doMockSignUp("I love uda", "I hate Uda", "NamTT20", "NamTT20");
		doLogIn("NamTT20", "NamTT20");

		// Upload a valid file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		String fileName = "FileUploadTest.docx";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();

		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		WebElement backToHomeButton = driver.findElement(By.linkText("here"));
		backToHomeButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertTrue(driver.getPageSource().contains("FileUploadTest.docx"));
	}

	/**
	 * Deleting file test case
	 */
	@Test
	public void deleteFile_then_doNothing() {
		// Create a test count
		doMockSignUp("I love Uda", "I hate Uda", "NamTT20", "NamTT20");
		doLogIn("NamTT20", "NamTT20");

		// Upload a valid file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		String fileName = "FileUploadTest.docx";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();

		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		WebElement backToHomeButton = driver.findElement(By.linkText("here"));
		backToHomeButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement deleteButton = driver.findElement(By.linkText("Delete"));
		deleteButton.click();

		Assertions.assertTrue(!driver.getPageSource().contains("FileUploadTest.docx"));
	}

	/**
	 * Adding credential test case
	 */
	@Test
	public void addCredential_then_doNothing() {
		// Create a test count
		doMockSignUp("I love Uda", "I hate Uda", "NamTT20", "NamTT20");
		doLogIn("NamTT20", "NamTT20");

		// Uploading note
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-new-credential-button")));
		WebElement addNewCredentialBtn = driver.findElement(By.id("add-new-credential-button"));
		addNewCredentialBtn.click();

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credentialModal")));
		WebElement credentialUrl = driver.findElement(By.id("credential-url"));
		credentialUrl.sendKeys("add url");
		WebElement credentialUsername = driver.findElement(By.id("credential-username"));
		credentialUsername.sendKeys("add username");
		WebElement credentialPassword = driver.findElement(By.id("credential-password"));
		credentialPassword.sendKeys("addPassword");
		WebElement saveChangesBtn = driver.findElement(By.id("save-credential-button"));
		saveChangesBtn.click();

		Assertions.assertTrue(driver.getPageSource().contains("add url"));
	}

	/**
	 * Editting credential test case
	 */
	@Test
	public void editCredential_then_doNothing() {
		// Create a test count
		doMockSignUp("I love Uda", "I hate Uda", "NamTT20", "NamTT20");
		doLogIn("NamTT20", "NamTT20");

		// Upload a note
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-new-credential-button")));
		WebElement addNewCredentialBtn = driver.findElement(By.id("add-new-credential-button"));
		addNewCredentialBtn.click();

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credentialModal")));
		WebElement credentialUrl = driver.findElement(By.id("credential-url"));
		credentialUrl.sendKeys("add url");
		WebElement credentialUsername = driver.findElement(By.id("credential-username"));
		credentialUsername.sendKeys("add username");
		WebElement credentialPassword = driver.findElement(By.id("credential-password"));
		credentialPassword.sendKeys("addPassword");
		WebElement saveChangesBtn = driver.findElement(By.id("save-credential-button"));
		saveChangesBtn.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("view-credential-btn")));
		WebElement viewCredentialBtn = driver.findElement(By.id("view-credential-btn"));
		viewCredentialBtn.click();

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credentialModal")));
		credentialUrl = driver.findElement(By.id("credential-url"));
		credentialUrl.clear();
		credentialUrl.sendKeys("edit url");
		credentialUsername = driver.findElement(By.id("credential-username"));
		credentialUsername.clear();
		credentialUsername.sendKeys("edit username");
		credentialPassword = driver.findElement(By.id("credential-password"));
		credentialPassword.clear();
		credentialPassword.sendKeys("editPassword");
		saveChangesBtn = driver.findElement(By.id("save-credential-button"));
		saveChangesBtn.click();

		Assertions.assertTrue(driver.getPageSource().contains("edit url"));
	}

	/**
	 * Test case for deleting credential
	 */
	@Test
	public void testDeleteCredential() {
		// Create a test count
		doMockSignUp("I love Uda", "I hate Uda", "NamTT20", "NamTT20");
		doLogIn("NamTT20", "NamTT20");

		// Upload a note
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		credentialsTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-new-credential-button")));
		WebElement addNewCredentialBtn = driver.findElement(By.id("add-new-credential-button"));
		addNewCredentialBtn.click();

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credentialModal")));
		WebElement credentialUrl = driver.findElement(By.id("credential-url"));
		credentialUrl.sendKeys("add url");
		WebElement credentialUsername = driver.findElement(By.id("credential-username"));
		credentialUsername.sendKeys("add username");
		WebElement credentialPassword = driver.findElement(By.id("credential-password"));
		credentialPassword.sendKeys("addPassword");
		WebElement saveChangesBtn = driver.findElement(By.id("save-credential-button"));
		saveChangesBtn.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("view-credential-btn")));
		WebElement deleteCredentialBtn = driver.findElement(By.id("delete-credential-button"));
		deleteCredentialBtn.click();

		webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("view-credential-btn")));
		Assertions.assertFalse(driver.getPageSource().contains("add url"));
	}

}
