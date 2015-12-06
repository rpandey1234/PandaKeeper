# Pre-work - *PandaKeeper*

**PandaKeeper** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Rahul Pandey**

Time spent: **12** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [x] Add support for completion due dates for todo items (and display within listview item)
* [ ] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [x] Add support for selecting the color label of each todo item (and display in listview item)
* [x] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [x] Used a viewpager so user can swipe between tasks on the detail view.  
* [x] Each task object has a title, additional details, color (can be used for prioritization), and due date.
* [x] Tasks in list view are ordered by due date. 

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/8TKqrnA.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Passing the task object between fragments caused me some confusion, and there's likely a cleaner way to do it. I'm also querying the SQLite database in each on click handler, so I'm wondering if there's a better way to do that without repeating code.

I also would like to better communicate between different parts of the app. For example, when one button of the color grid is tapped (for selecting a task's color label), I'd like to de-select the other buttons. I know it is possible to pass events using EventBus, so a larger app could certainly use that to streamline communication.

Another library which would have made my code much cleaner would have been Butterknife.

## License

    Copyright 2015, Rahul Pandey

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.