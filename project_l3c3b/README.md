# Music Management Program

## Organizing and Ranking music

This program will allow users to input songs they like, artists, and albums that they are interested
in. The program will have music genres that are input with
the following three data sets: 
- song
- artist 
- album.

With each class of data, the accompanied **genre** for each, is tallied
up and displayed as the users most listened to and enjoyed genre. People who 
are looking for new music, and an overall understanding of what music
they already listen too are the intended audience for this app. This project interests me
because I am always interested in organizing my music in a way that allows me to 
know what genre I am interested in at the moment. Since my taste is always changing,
I'm usually looking for new songs frequently, this program should help better understand
what overall music to look for.
##User Stories
- As a user, I want to be able to input a song
- As a user, I want to be able to input an artist
- As a user, I want to be able to input an album (list of songs)
- As a user, I want to be able to edit my inputs
- As a user, I want to be able to save and load the album to and from file
- As a user, I want to be able to save and load the album to and from file
- As a user, I want to be able to save and load the library to and from file
- As a user, I want the songs in my album to also be stored in the songs section
- As a user, I want the artist from my songs and albums to be stored in the artist section as well

##Phase 4: Task 2
I chose to make my Artist and Song class robust by adding two exceptions.
I implemented a map for my genre recommendations in MusicManagementProgram

##Phase 4: Task 3
Looking at my UML class diagram, things I would have
done differently are 
- implement more type hierarchy's to avoid unneeded coupling. 
- Within the diagram, I would have liked to get rid of the dependencies that MusicApp
has with Album, Artist, and Song to one association with Library, and then Library 
should have the only associations with the base classes.
- I would also only have MusicManagementProgram have two associations, one with
MusicApp and one with BackgroundMusic, deleting the 
association with Library, because there is already
one between Library and MusicApp.