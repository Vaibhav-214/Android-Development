# Android-Development
It contains my android development projects while learning 


Week6 - An app made using Kotlin, Jetpack Compose, MVVM Architecture, Hilt for dependency Injection ,Retrofit,Paging3, Coil. WorkManager, Preferences datastore, etc.

It is a mini social media app
It fetches users from an api, uses pagination to list users, we can click on any user and it will fetch profile information about that user like, profile picture, posts, followers count, etc.We can click any post to see post details.We can delete any post and we can create posts, all using network requests.

I have used WorkManager to schedule deleteing of post after 5 seconds of clicking it.Work Manger is a jetpack library by google to perform background deffered tasks.It will remaain shceduled even if app closes or device restarts.

Added swipe to refresh functionality using accompanist library.

I have also added multi langugae support with Hindi and English.And used preferences datastore to save the users choice, so if the app restarts then too the selected language will persisit.

  

https://user-images.githubusercontent.com/94449143/234789540-f20d6190-ba13-44c4-919f-210b0a12c6e5.mp4

