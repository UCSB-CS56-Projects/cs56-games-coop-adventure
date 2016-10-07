cs56-games-coop-adventure
=========================

Point and click adventure game about the Student Housing Coops

project history
===============
```
 W14 | bronhuston 4pm | nissayeva | (erdinckorpeoglu) Point and click adventure game about the Student Housing Coops
```
=======
To run:
Load files to computer (or clone repo)
In terminal, go to the folder in which all of the files are kept
Use the command "ant run" to begin the game
<br>
<br>Click on buttons at the bottom of the screen to travel to different rooms of the house
<br>Click on characters to have "conversations" and learn about the Student Housing Coops
<br>Click on objects around the room to animate them
<br>
<br>
<br>Notes for developers:
<br>This program may not run on some systems and may receive this error message:
<br>   "[java] Exception in thread "main" java.lang.OutOfMemoryError: Java heap space"
<br>X11 may also be necessary to properly run the program
<br>
<br>All graphics should be put into the source folder - 
<br>  Backgrounds and buttons are kept in the src folder
<br>  Other images kept in their respective folders in the src directory depending on their location
<br>  
<br>  To add graphics:
<br>    (In an existing room)
<br>    - Store images (pngs) in appropriate room folder in src directory
<br>    - All interactive object should be name accordingly:
<br>      ex: Livingroom_Item_nameofItem.png
<br>          Livingroom_Item_nameofItem2.png (image or gif to be displayed upon click event)
<br>    - New directories must be created in the src folder for new rooms
<br>      ex: src/BathroomThings
<br>    - A new Room object must also be created in Game.java under the initiate() method (see src/Game.java and src/Room.java)
<br>      
<br>
<br>A couple screenshots:
<br>http://postimg.org/image/o7ikv19g5/
<br>http://postimg.org/image/k7xe1ejyj/
