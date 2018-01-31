import pyrebase

config = {
  "apiKey": "AIzaSyCw0oGuBwSIfMGJbghmuaw0CoftNKaUr4w",
  "authDomain": "lecbuddyimges.firebaseapp.com",
  "databaseURL": "https://lecbuddyimges.firebaseio.com/",
  "storageBucket": "lecbuddyimges.appspot.com"
}

firebase = pyrebase.initialize_app(config)

auth=firebase.auth()
user=auth.sign_in_with_email_and_password("ngimhana94@gmail.com","N0775744613ng")

storage=firebase.storage()
storage.child('Temp1.txt').put('/home/gimhana/Desktop/abc.jpg',user['idToken'])



