# SimplySnap

##### Created by Tommy Bracciata, Danny Tran, and Emma Hubka

## Overview 
#### Simply snap a pic of your textbook and our app will capture the text and summarize it. You will have access to both the unsummarized text and the summarized text. There are two ways to take advantage of our app: 
1. Take a picture of the text
2. Upload an image

## Inspiration
### While reading long textbooks on an early September night, one of our team members found themselves buried in a stack of textbooks with no hope of finishing the reading before the early hours in the morning. After a long night of reading textbooks, our team member had an idea for a mobile app that summarizes long bodies of text. This app has the potential to help students reduce the time spent searching through textbooks, and allows them to focus on the overall concepts. 


## How we built it
### We began by designing our application on paper before programming it. We developed a paper prototype based on the outline to model how our app would respond to different actions, such as a button being pressed, and created several different iterations of the app each building off of the last. The benefit of the paper prototype was we were able to catch and fix potentially confusing parts of our app before we invested more time into building the application on the computer. After the paper prototype, we did research on color schemes. From our research, we found blue aided productivity and green helped concentration, so we decided to get the best of both worlds and decided on a teal color scheme. 

### After we completed the design process, we moved into the implementation of our app using Android Studio. To save time we divided the work among our team. We had one person specialize in the user interface, one person specialize in the backend use of the APIs, and one person design the logo, create the documentation, and help the other team members debug their sections. For the user interface, we used XML. This posed a challenge as none of our team members had used XML before. With the help of documentation, we were able to create a user interface to our liking. In the backend, we used Java and two APIs for our app. The first API, Google Firebase Text Recognition, recognizes text from images, and the second API, Unirest-Java, summarizes the text. We had not used either of these APIs before, so this also posed a challenge. All of us did have experience programming in Java though, so this made learning the APIs easier. To create the logo for our app we used Photoshop, which one of our team members had previous experience in. 

## Challenges we ran into
### The documentation for the two APIs we used was incomplete. We had tested several different APIs for summarization, but some were outdated. We did not have much experience with HTTP Repsonses for getting information. As a result, we had quite a few obstacles. 
### The documentation for the XML was also lacking. It was updated recently, within the past couple of years, and no longer supported a lot of the stuff in examples online. This made it difficult to find a solution to our problem quickly as we had to find a recent solution. 

## Accomplishments that we're proud of
### We are proud of the combining of different team members work. We were able to merge ideas and research to create the best design possible, and we were able to combine the different parts of our implementation (user interface, backend, etc) cohesively into one final product. 
### We are also proud of the implementation of the two different APIs. This was a difficult task and we had to change APIs several times, but in the end, we were able to implement both of them.
### We are also proud of the logo. Graphic design is not any of our strong suits, so being able to create a logo from an idea on paper was an accomplishment we were proud of. 

## What we learned
### We learned how to create an Android app
### We learned how to implement different APIs and multiple APIs on the same project
### We learned XML

## What's next for SimplySnap
* Create a IOS version
* Create a desktop application
* Add support for other languages
* Add support for PDFs
