Welcome to the README for your ghostpet. If you're here, you're probably trying to make your own new ghost or change the features of the one you have. (If that's not why you're here, what you're looking for is somewhere else, sorry.) I'll step through the various components, what they're there for, and how to change them.

IMPORTANT NOTES

* Doing things like "adding a new thing for the ghost to say" or "changing a thing the ghost says" does NOT require any coding knowledge. I'll go into greater detail soon, but the short version is, anything that only affects how the ghost looks or what the ghost says can be done by changing around some information in the JSON files, and doesn't require actual coding.
* When creating new behaviors, ALWAYS name the class and the JSON file the same thing. If you have "XBehavior.java", then the information file for it should be "XBehavior.json", because otherwise the behavior can't find the information associated with it.
* Conversely, do NOT use the word "Behavior" in any file that you put into the resource folder that ISN'T a .json file for a Behavior-derived class. It causes things to go wrong.

[HOW TO CHANGE THE TEXT OUTPUT]
This is the easiest thing to change, so I'll start with that. First, find the JSON file for the behavior whose text you want to change in the Resources folder. (If you're looking for the name of the ghost or what the ghost calls you, that's in GhostJson.) Then, find the text that isn't what you want, and just rewrite it. (Note: This means that you change the text on the RIGHT side of the semicolon. Changing the text on the left side breaks things.) So long as you're only changing text inside quote marks, not any of the text outside it, nothing should break. 

Also: There are two variables in the text strings, $NAME$ and $GNAME$. When the text gets used, $NAME$ gets replaced by the text for "UserName" in GhostJson, and $GNAME$ is replaced by the text for "ghostName". 

[HOW TO CHANGE HOW LONG THE TEXT DISPLAYS FOR]
Second easiest thing. Most behavior files have an actTime variable, which is the time the ghost performs that behavior for in seconds. Change it to change how long it displays. This isn't a requirement, but it's recommended that behaviors where the ghost asks something have long actTimes, so the user has a sufficiently long chance to respond.

[HOW TO CHANGE THE IMAGES USED]
The images the ghost uses are stored in the Images folder under the resources folder. Adding more just involves dragging a .PNG file into that folder. Images should have no background (no, a flat color background doesn't count, it needs to be just the alpha channel), and while the size can vary it's recommended that you keep it small so you can still work around it. For reference, the stick figures are 108x192. All the images used should be the same size, or at least the same canvas size.

Once the image has been added in, you need to update the JSON files. Basically, for every JSON file that uses the old image, change the name to the name of your new image. (Or, if you're just replacing an image, you can name it the same thing as the previous one to save time.)

If you're adding in an entirely new image (not a replacement for the stand, speak, or pet images), you do it pretty much the same way. Go through the JSON files, and wherever you want your new image to display, replace the name of the image currently in use with the name of yours. 

[CHANGING THINGS IN DIFFERENT BEHAVIOR FORMATS]
There are a couple of behavior types where the change isn't as obvious to make. I'll go over a few of them here.

For the "LongTalk" behaviors, with the three groups stmtChains, timeChains, and imgChains: Each one of those groups has an outer pair of square brackets, and then multiple "inner pairs." Each inner pair is connected to that same inner pair in the other two "chain" groups - the first pair in stmtChains is connected to the first pair in timeChains and imgChains. Basically, each individual value in the chains is the equivalent of part of a normal Behavior - stmtChains is the statement said (yes, there's only one of them), timeChains is how long it's said for, and imgChains is the image used. From there, change each one as you see fit, according to the others.

For the "Ask" behaviors, there's two additional variables - BehaviorList and actStatements. ActStatements is, quite simply, the list of choices you have available - they get printed out in the text box. BehaviorList is the list of behaviors that get executed when the option is chosen (listed in the same order as the choices in actStatements). You can change what an option does by exchanging the behavior listed there for the behavior you want.

<THINGS TO DO YET>
* Implement a way for the ghost to check your email and calendar
* Implement a way for a given behavior to "choose" which statement chain the LongTalk behavior uses
* Ensure the word-wrapping actually works
* Make Gradle put things in the right places when build is used
* Improve the art, probably