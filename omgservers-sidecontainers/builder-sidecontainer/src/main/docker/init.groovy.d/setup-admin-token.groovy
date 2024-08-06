import hudson.model.User
import jenkins.security.ApiTokenProperty

def userId = System.getenv("JENKINS_ADMIN_ID")
def userToken = System.getenv("JENKINS_ADMIN_TOKEN")

def user = User.get(userId)
user.getProperty(ApiTokenProperty.class).tokenStore.addFixedNewToken("integrations", userToken)
user.save()