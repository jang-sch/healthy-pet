# healthy-pet

## Overview
A pet health app that will allow the user to keep track of their pet's health and life. The app will keep track medications, eating habits, exercise habits, vaccination and immunization records, veterinarian appointments, behavior or changes in behavior, and general health. For a variety of different pets including dogs, cats, rodents, lizards, and exotics of differing breed and ages, can upload profile picture of pet.

## Health

* Veterinarian Information
  * Preferred veterinarian/primary vet
  * Past appointments
  * Upcoming appointments reminders

* Medication management
  * Medication schedule
  * Medication symptoms/reactions

* Vaccination and Immunization
  * Records
  * Future upcoming immunizations

* Symptom Tracker


## Lifestyle

* Eating
  * Eating Habits
  * Portions
  * Weight
 

* Voiding Habits

* Exercise Habits

* Behaviors

## Other
* Resources
  * Local Veterinarians
  * Local Urgent Cares
  * Local Animal Emergency Care
  * Poison Control Numbers

* Pet information is stored in remote database
* Export whole or parts of pet record as pdf
* Allow for temporary access to pet profiles for pet sitters via the app
* Able to create multiple pet profiles 
* For both Android and Apple

## Security
* Implement security for records and comply with animal HIPAA laws.

## Environment Setup
1.Go to https://nodejs.org/en/download/ and download the LTS version of Node

2.Install Git on your system: https://git-scm.com/download/win. When you install Git for Windows this will also include the GitBash terminal, which is what we would suggest using in this course. Since it is a bash emulator, it will allow you to type the same commands that Stephen types even though he is using a terminal on macOS.
3.Download the boilerplate rn-starter file
4.Open your Windows File Explorer and go to the Downloads directory.
5.Find the zip archive that was downloaded - which was likely named "rn-starter.zip". Right-click on this zip file and select "Extract All":
6.Set the extract destination to your user's directory - my user's name is "dev". This step is very important since the file watchers React Native / Expo use will have issues with the Desktop, Downloads, or Documents directories. DO NOT attempt to use a network drive or cloud-synced drive like Google Drive, Dropbox etc. The project files should be extracted to the C:\Users\YourUserName directory.
7.Open the gitbash terminal application
8.In the terminal change into your user's directory by running cd ~/
9.Run ls in the terminal. You should see a folder called "rn-starter".
Note - if you did not follow the earlier instructions to extract the project files to the "C:\Users\YourUserName" directory then you will not find the "rn-starter" folder in this location. You will need to cd into the directory you have extracted it to.
10.Change into the project directory by running cd rn-starter
11.Run ls again to view the contents of this directory. Make sure there is a package.json file. If you do not see a package.json file, then you are in the wrong directory.
12.Once you have verified that you are in the correct directory, run npm install --legacy-peer-deps
13.Install the expo-cli tools by running npm install expo-cli --global
14.After the dependencies have been installed, attempt to run npm start
15.If you get errors about 'expo-cli' not being recognized make add NPM to the environment variables.
16.Open the Windows search bar and type "environment". Select "Edit the System Environment Variables"
17.Click "Environment Variables":
18.Double Click "Path" under "System Variables":
19.Click the "New" button and add the following path:
%USERPROFILE%\AppData\Roaming\npm
20.Click OK and apply changes.
21.Close gitbash and reopen. Change back into rn-starter folder
22.Run npm start. The metro bundler should open. 
