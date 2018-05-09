<h2>Android app authenticating login with PHP script<h2>
<p>Code Explained</p>
<ol>
	<li>MainActivity{} will check whether user is logged in by using checkLogin() method in UserSessionManager{} class (This is to decide whether MainActivity{} or LoginActivity{} to be rendered)</li>
	<li>If user is not logged in, it will render LoginActivity{} as new intent</li>
	<li>LoginActivity{} will trigger BackgroundWorker{} class, doInBackground() method</li>
	<li>doInBackground() method use HttpURLConnection to post user credential to URL_LOGIN ( a constant value, this value should be remote URL to auth against)</li>
	<li>Remote call returns either Successful or Failed string message</li>
	<li>If successful, then it will render the MainActivity{}</li>
</ol>