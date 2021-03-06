# Project3_Group3_Android : "Choice"

## Team Members (Group 3)
 - Turipamue Mbetjiha ((https://github.com/TuriM98)
 - Pedro Gutierrez Jr. (https://github.com/PedroG1018)
 - Olisemedua Onwuatogwu (https://github.com/Youngphil5)
 - Jeffrey Dobbek (https://github.com/JeffreyDobbek)
 
 ### Project Description
 
This is an app that helps users to make a choice. For example, it could be choosing the color for your room or picking food to eat. Some people are indecisive about making a choice; getting other people's votes can help them decide. This app makes decisions easy. Choice will allow users post a list of choices (as picture or text) and the app will help the user make a choice out of his posted list of choices using the result from the online poll. MVP will include account creation, post creation, viewing posts, voting on a choice using likes and sending post.

### Web Repo

https://github.com/Youngphil5/Choices-proj3-

### Tech Stack
mySQL -> Replit -> Node.js -> Express -> Android

### Mobile End Screenshots
<img width="200" alt="Screen Shot 2022-05-17 at 3 13 16 PM" src="https://user-images.githubusercontent.com/49994182/168920883-100be9e2-339b-4961-a40e-09ebe3c5a2f0.png"><img width="200" alt="Screen Shot 2022-05-17 at 3 13 54 PM" src="https://user-images.githubusercontent.com/49994182/168921611-127fd38d-4808-4ba6-bf82-bb32a9ef75a0.png">
<img width="200" alt="Screen Shot 2022-05-17 at 3 13 38 PM" src="https://user-images.githubusercontent.com/49994182/168920921-c14bb8e6-8001-4303-b39f-14b6a06387e2.png">
<img width="200" alt="Screen Shot 2022-05-16 at 11 58 08 PM" src="https://user-images.githubusercontent.com/49994182/168921021-1206e9b1-612c-4891-93cb-321f95d8331c.png">
<img width="200" alt="Screen Shot 2022-05-17 at 3 14 55 PM" src="https://user-images.githubusercontent.com/49994182/168921047-561654e6-f937-4efd-a33c-8133cc58102a.png">
<img width="200" alt="Screen Shot 2022-05-17 at 3 15 04 PM" src="https://user-images.githubusercontent.com/49994182/168921325-7304a66b-157f-496f-b90c-1ff69e0d6ae7.png">
<img width="200" alt="Screen Shot 2022-05-17 at 3 15 11 PM" src="https://user-images.githubusercontent.com/49994182/168921468-1ef2b7dd-8cb4-4c02-9a77-1d878ed35e2c.png">

### Wireframe (not final)

<img width="300" alt="Screen Shot 2022-04-10 at 8 13 14 PM" src="https://user-images.githubusercontent.com/49994182/162660934-b738f2a5-d3af-4847-85ca-e53e999ea8a0.png"> <img width="300" alt="Screen Shot 2022-04-10 at 8 13 26 PM" src="https://user-images.githubusercontent.com/49994182/162660940-7d9c20f1-ce31-4522-a42d-69b683fceafd.png">
<img width="300" alt="Screen Shot 2022-04-10 at 8 13 51 PM" src="https://user-images.githubusercontent.com/49994182/162660945-1088ae69-6175-4d90-ba41-87858292c356.png">
<img width="300" alt="Screen Shot 2022-04-10 at 8 14 03 PM" src="https://user-images.githubusercontent.com/49994182/162660964-73f99ef1-fe8c-4635-9f61-8540e118be31.png">
<img width="300" alt="Screen Shot 2022-04-10 at 8 14 17 PM" src="https://user-images.githubusercontent.com/49994182/162660968-1ccc529e-86e4-4bc1-a4f0-1e340bed5ba1.png">
<img width="300" alt="Screen Shot 2022-04-10 at 8 14 37 PM" src="https://user-images.githubusercontent.com/49994182/162660973-f3a8c2df-c35d-4d62-b5c5-429f878c39b3.png">


### API Endpoints (not final)

Create Account - POST [url]/register?email={email}&name={name}&password={password}<br>
Login - POST [url]/login?email={email}&password={password}<br>
Logout - GET [url]/logout<br>
Delete Account - DELETE [url]/logout<br>
Profile - GET [url]/profile?email={email}<br>
Change Info - PUT [url]/profile?email={email}&name={name}&password={password}<br>
Landing Page - GET [url]/home<br>
Create Post - POST [url]/home?userId={userId}&caption={caption}<br>
Vote on a Post - PUT [url]/home?postId={postId}&imageId={imageId}&numVotes={numVotes}<br>
Leave Comment - POST [url]/home?postId={postId}&comment={comment}<br>
End a Poll - PUT [url]/profile?postId={postId}<br>
Delete Post - DELETE [url]/profile?postId={postId}<br>

#### Entity Relation Diagram (not final)
https://lucid.app/lucidchart/6c5f6941-ec01-453e-96c7-be3e5150a49d/edit?beaconFlowId=52F46FDEE0894A4E&invitationId=inv_8cfdda85-75ad-44a2-a4c5-4b49d8bdb6dc&page=0_0#

<img width="868" alt="Screen Shot 2022-04-10 at 3 43 55 PM" src="https://user-images.githubusercontent.com/63982593/162649903-e89c2491-9c9b-47b0-b967-e1ce01f5334c.png">

As of now, there are four tables that communicate with each other via their their respecitive IDs. This done to ensure that the correct posts are linked to the correct users, images and comments are linked to the correct posts, etc. Subject to change during development.

### Useful Links

Android Studio Upload Picture Tutorial - https://www.youtube.com/watch?v=e8x-nu9-_BM
