# mvvm_android

**android example app with MMVM**

**Ideas**

* The FamilyNoteApp was created with MVVM design
* When user logedin and come to the noteboard, the notesearchviewmodel will be triggered to call the server to get all the initial today's notes and shown on the noteboard. If choose local search, the search content is the one on the screen, if choose global search, notesearchmodel will be triggered to call the server with input keywords and shown on the noteboard.
* If user click the notepad, user can submit a new note. When a note submmited, if you user come back to the noteboard, the new note will be shown beacause of observer in the noteboard.
* When user come to the settings, filter by from, to or date, then go back to noteboard, it shows the filtered notes by seleted from ,to and date.
* If user want to add some family members, click the add family members, then come to the add family member page, fill in the names and click add, the family members are posted to the server. When user come to notepad, the new added family members are shown in the spinner for from and and to textview. If user come to the settings and click the check notes from or to, the new added family members are also added in the name list.

* The view part is mainly the viewControllers, such as the login activity, register activity, noteboard fragment, notepad fragment, settings fragment, family member fragment.
* The viewmodel part includes all the view models such as notesearchviewmodel, addfamilymemberviewmodel etc.
* The model part is mainly web service call. 
* The web service server is in another repository in my github called App-server, the autnenatication is another repository Authentication-server
(Note: the app server and authentication server is created separately, mainly for future use, other servers can also use the authentication server, and the authentication server can be deployed to other places if needed)
* The webservice call framework was encapsulated as a library called restService, which mainly use the Volley to do the http request.
* The restService library can be shared with different projects, and there are interfaces which can be used to parse the rest result.

* The FCM notification is in the branch with_FCM_notification.

**Demo**

![LoginScreenshot](https://github.com/kelci2017/mvvm_android/blob/screenshots/familynoteapp.gif)

![LoginScreenshot](https://github.com/kelci2017/mvvm_android/blob/screenshots/login.png)
![LoginScreenshot](https://github.com/kelci2017/mvvm_android/blob/screenshots/register.png)
![LoginScreenshot](https://github.com/kelci2017/mvvm_android/blob/screenshots/noetboard.png)
![LoginScreenshot](https://github.com/kelci2017/mvvm_android/blob/screenshots/notepad.png)
![LoginScreenshot](https://github.com/kelci2017/mvvm_android/blob/screenshots/settings2.png)
![LoginScreenshot](https://github.com/kelci2017/mvvm_android/blob/screenshots/settings1.png)
![LoginScreenshot](https://github.com/kelci2017/mvvm_android/blob/screenshots/addfamilymember.png)
![LoginScreenshot](https://github.com/kelci2017/mvvm_android/blob/screenshots/notification3.png)
![LoginScreenshot](https://github.com/kelci2017/mvvm_android/blob/screenshots/notification2.png)
