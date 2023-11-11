<div><h1>Sora Tasker</h1></div>
<h4>What is this? 
This is some sort of Tasker like from STALKER series.</h4>
TO DO:
1) Make types for Tasker => Make it storeable
2) Make CheckBox clickable
3) Make edit activity and button to open new intent
4) Create layout
5) Types for Tasker 


So we have a global list of all tasks that will be shown to RecyclerView. 
Possible ways to write application:
1) To write API-like app. This will make some sort of operation with database
2) Store - Edit app. Application will get EVERYTHING from database then proceed to edit it. Then on exit it will override all data in DataBAse. 

I have chosen first option. 
First off all: When application started the main goal to show remain tasks (that is un done).
Next when we need to show active/deactive tasks, so we changing our tasks list.


To not delete and then re-save current tasks we need to insert them by id.

Possible types of Tasks:
* Single task - like from original Stalker
* Every day task - this task will occur every day. (Maybe better will be to add n-occur task, for example this task will occur every n hours/days/weeks also to add counter like it will occur n times every n time)
* Counter task - task that will count something like amount of done tasks / days without playing

Maybe good experience will be to add statistics for this

And also an organizer via calendar.

# My git helper
Online source for md files editor https://stackedit.io/app#

Adding all to remote repo: git add .
Commiting: git commit -m "message for commit"
Pulling: git pull
(If something goes wrong): git push
git status: git status
To update from repo: git remote update   =>  git pull 

