Base Defense Modification
=========================
The base documentation mod allows you to keep out all the evil people you never invited
to your parties!

With the help of our new patented security system, you'll be able to keep out both unloved
Soaryns and any other living creature which might distract you while working on your world
domination plans.

Installation
------------
This modification requires Forge for Minecraft 1.7 (at least build 1060). And has been tested
against version 10.12.1.1060.

To install the mod you will just need to drag the jar into your modification folder. DO NOT
TOUCH MINECRAFTS ORIGINAL JAR FILES. REFER TO THE MINECRAFT FORGE DOCUMENTATION FOR MORE
INFORMATION ON HOW TO INSTALL FORGE.

Use
---
The mod bases around the Defense Station which connects all components. This includes
surveillance cameras and security bots.

To connect multiple so called "entities" you will need to use the Wireless Tuner:
1. Shift-Right-Click on the Defense Station
2. Right-Click on any other entity (e.g. Security Bot or Surveillance Camera).

A network consists at least of one Defense Station, a Surveillance Camera and one
Security Bot.

Please note that Security Bots will not attack unless a camera detects suspicious
activity. Furthermore Security Bots will return to their original positions after
a specific amount of time passed without any suspicious events.

Players which are allowed to pass the security cameras without causing the alarm
to trigger can be defined at the Defense Station. To add new players their names
have to be entered one by one into the input field below the user list.

Please note that users have to be online in order to add them to the list.

Crafting
--------
The following recipes are available within this modification:

Camera Lens
###########
X-X	X = Glass
-X-
---

Surveillance Camera
###################
XXX	X = Iron Block
XYZ	Y = Ender Pearl
XXX	Z = Camera Lens

Shapeless recipe:
	- Surveillance Camera (Mob Enabled)

Surveillance Camera (Mob enabled)
#################################
Shapeless recipe:
	- Surveillance Camera
	- Bone

Wireless Tuner
##############
-Y-	Y = Stick
XZX	X = Iron Ingot
XXX	Z = Ender Perl

AI Processor
############
XYX	X = Stick
YZY	Y = Iron Ingot
XYX	Z = Redstone Dust

Security Bot
############
XYX	X = Iron Block		W = Minecart
XZX	Y = Wireless Tuner
XWX	Z = AI Processor

Defense Station
###############
XYX	X = Iron Block
XZX	Y = Wireless Tuner
XXX	Z = AI Processor

Use in modpacks
---------------
You may use the modification in modpacks as long as you do comply with the license
terms applied to this modification (see below).

Contributing
------------
Changes to the mod may be pushed to GitHub at https://github.com/LordAkkarin/BaseDefense
(please refer to the GitHub documentation on pull requests for more information on
how to fork a repository and submit changes back to the upstream project).

Contributions are accepted as of the 19th of May at 12:00 AM (GMT).

Additional Notes
----------------
This modification has been developed as a submission for the 4th Modjam (see
http://mcp.ocean-labs.de/modjam/ for more information).

Known Problems in the current release
-------------------------------------
* Security Bots may get "stuck" - Removing them by shift-right-clicking them with
  the wireless tuner and syncing them again will fix the problem
* Pairing Security Bots to the Defense Station may not work if the Security Bot
  is selected first
* Security Bots to not display the correct death message
* All items use textures instead of renders of their respective models

License
-------
Copyright (C) 2014 Evil-Co <http://wwww.evil-co.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.