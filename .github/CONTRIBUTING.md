# Contributing

## Issues

**[GitHub issues](https://github.com/Abrynos/ShoppingList/issues)** page is being used for "todo" list, regarding both features and bugs. It has **strict policy** that applies to everybody - GitHub issues are **NOT** technical support, it's a place dedicated **only** to bugs and suggestions. It's **not** the proper place for technical issues, general discussion or questions that are unrelated to development (such as "How do I install this app?"). In short, GitHub is for the **development** part only, and all issues should be **development-oriented**. You have **[GitHub discussions](https://github.com/Abrynos/ShoppingList/discussions)** for general discussion, questions, technical issues and everything else that is not related to development. If you decide to use GitHub issues, please make sure that you're in fact dealing with a bug, or your suggestion makes sense, preferably by asking in the discussions section first. Invalid issues will be closed immediately and won't be answered - if you're not sure if your issue is valid, then most likely it's not, and you shouldn't post it here. Valid bugs/suggestions will be forwarded from the discussions section and added as GitHub issues by us, if needed.

Examples of **invalid** issues:
- Asking how to install the application
- Having technical difficulties installing the application in some environment, encountering expected issues caused by the user's neglect
- Reporting problems that are not caused by the application, such as problems with other apps, unsupported operating systems and similar things
- Other activities that are not related to development in any way and do not require any development action from us

Examples of **valid** issues:
- Reporting incorrect behaviour that requires a code correction from us
- Posting a valid development suggestion on improving the project
- Correcting mistakes in our documentation or in likewise places that we're in charge of
- Other activities that directly benefit development and require a development action

---

### Bugs

Before reporting a bug, you should carefully check if the "bug" you're encountering is in fact a bug and not technical issue. Typically technical issue is **intentional behaviour which may not match your expectations**, e.g. the number `1` not being shown by default even if you think it should. If you're not sure whether you're encountering a bug or technical issue, please avoid using GitHub issues and go to the discussions section instead.

When reporting a bug, posting reproduction is **mandatory**. You want us to fix the bug you've encountered, then help us instead of making it harder - we're not being paid for that, and we're not forced to fix the bug you've encountered. Include as much relevant info as possible - everything you consider appropriate, that could help us reproduce the bug and fix it. The more information you include, the higher the chance of the bug getting fixed. **If nobody is able to reproduce your bug, there is also no way of blindly fixing it**, so it's in your best interest to **make us run into your bug**.

It would also be cool if you could reproduce your issue on latest pre-release (and not stable) version (if available) as this is the most recent codebase that could include not-yet-released fix for your issue already. Of course, that is not mandatory, but it's entirely possible that your bug is already fixed, just not released yet.

---

### Feature requests

While everybody is able to create suggestions how to improve the project, GitHub issues is not the proper place to discuss whether your enhancement makes sense in the first place - by posting it you already **believe** that it makes sense, and you're **ready to convince us how**. If you have some idea but you're not sure if it's possible, makes sense, or fits the projects purpose - you have the discussions section where we'll be happy to discuss given enhancement in calm atmosphere, evaluating possibilities and pros/cons. This is what we suggest to do in the first place, as in GitHub issue you're switching from **having an idea** into **having a valid enhancement with a general concept, given purpose and fixed details - you're ready to defend your idea and convince us how it can be useful for the project**. This is the general reason why many issues are rejected - because you're lacking details that would prove your suggestion being worthy.

> In the lifetime of an Open Source project, only 10 percent of the time spent adding a feature will be spent coding it. The other 90 percent will be spent in support of that feature.

This especially includes maintenance of that feature, such as documenting it, keeping that documentation up-to-date, ensuring this feature won't break between one version and another, actively supporting that feature (including answering people how it works, why it doesn't work the way they want, and why it works like that), on top of all usual code maintenance, refactoring and writing it in the first place. We code this in our free time, and our free time is not infinite. Moreover, even if it was infinite, we'd still not be able to please everybody, as it's simply not possible to create a something that satisfies everyone. Developing any kind of software project is always about compromise - you can't possibly have everything. Using this for checking the weather or reminding you to feed your cat isn't necessarily within the program's scope. You don't have to agree with us, but you have to respect our decision as project maintainers when you make a suggestion that simply isn't what the project was designed to do. This is an open-source project, if you can't live without some feature then you can always code it **yourself**, and if that's too much for you, then like it or not, but you have to respect our decision whether **we** will decide to code it.

Lastly, our code strikes to be perfect, so we generally won't reinvent the wheel by coding something that is already possible. This does not mean that you can't suggest another way to deal with a particular problem, as long as you follow everything stated above, and you're able to defend your suggestion and prove how it's useful/better than what is already possible - why the current solution doesn't work for you, and new one would. Once again - reasoning is what mainly matters when dealing with suggestions. Lack of good reasoning is the main reason why it's hard to convince us into doing something, as we know our code better than anybody else, including everything that is possible to do with it, so if you can't tell us why your solution works better than the old one, then there is not enough reasoning to change/improve it in the first place.

In any case, you should be able to explain to us in the issue why you consider your enhancement as useful, why do you think that adding it to the project is beneficial for **all users**, not just yourself. You have to convince us to follow your logic. If your suggestion indeed makes sense, or can be considered practical, most likely we won't have anything against that, but **you** should be the one pointing out advantages, not us. Creating an issue means that you're asking us to spend that very limited free time explained above on your case specifically, so you must have some valid arguments that would defend our main question "why". If you can't answer it, we won't answer it for you.

---

## Merge requests

Merge requests are a bit different compared to issues, as in a merge request you're asking us to review the code and accept it, unless **we** have a reason against it. Very often we won't have enough arguments to accept given suggestion and code something, but we also won't have enough arguments **against** given suggestion, which makes it possible for you to code it yourself, then send a merge request for review, and hopefully include your feature, even if we wouldn't code it otherwise. All of that is possible thanks to the fact that when dealing with merge request, **we** are in position to find reasoning against it, and not necessarily you defending your own code.

In general any pull request is welcome and should be accepted, unless there is a strong reason against it. A strong reason includes mainly only things we directly do not agree with, such as features that are greatly outside the projects scope (to the point it'd hurt overall maintenance), or likewise. If there is nothing severe enough to justify rejecting a merge request, we'll tell you how to fix it (if needed), so we can allow it to be merged. If you're improving existing code, rewriting it to be more efficient, clean, better commented - there is absolutely no reason to reject such a merge request, as long as it's in fact correct. If you want to add a missing feature, and you're not sure if it should be included, for example because you're not sure if it fits the scope - it won't hurt to ask before spending your own time, preferably in the discussion section, so we can evaluate the idea and give feedback instead of accepting/rejecting the concept which is usually happening with GitHub issues - after all you want to code it yourself, so you shouldn't use GitHub issues that are being used for expecting us to add things. Still, as stated above, our entire GitHub repo is dedicated to development part, so feel free to post an issue in which you'll ask if given feature would be accepted in merge request, if you prefer that way.

Every merge request is carefully examined by our continuous integration system - it won't be accepted if it doesn't compile properly or causes any test to fail. We also expect that you at least barely tested the modification you're trying to add, so we can be sure that it works. Consider the fact that you're not coding it only for yourself, but for other users as well.

This is an open-source project. It's not our purpose or objective to make you problems or forbid you from improving our project, especially if you have a decent idea how to do so, therefore don't be afraid of making a suggestion first, then implementing it, after your suggestion is confirmed to make sense. Even if you're not completely sure how you should achieve a particular goal, you can still send a merge request with your own solution and get a feedback in code review regarding it. If you're sending a merge request, we expect that you did your best in it code-wise, so make sure that you're proud of your code, same like we are proud of ours. Also don't be afraid or in a hurry to fix your code based on our review - it's **for you** so you can make your merge request better while learning about exact reasons why your previous solution wasn't the best one.

> Always code as if the guy who ends up maintaining your code will be a violent psychopath who knows where you live.

~John Woods

---

### License

This project is using the **[GPL License version 2](https://github.com/Abrynos/ShoppingList/blob/main/LICENSE.txt)**.

> You may modify your copy or copies of the Program or any portion of it, thus forming a work based on the Program, and copy and distribute such modifications or work under the terms of Section 1 above, provided that you also meet all of these conditions:
> 
> [...]
> 
> You must cause any work that you distribute or publish, that in whole or in part contains or is derived from the Program or any part thereof, to be licensed as a whole at no charge to all third parties under the terms of this License.

---

### Code style

Please stick with our code style when submitting merge requests. In the repository you can find our **[Project.xml](https://github.com/Abrynos/ShoppingList/blob/main/.idea/codeStyles/Project.xml)** file that is being used by **[Android Studio](https://developer.android.com/studio)**.

Personally we're using **[Android Studio](https://developer.android.com/studio)**, so no other action is needed after opening the solution file. If you're using any other IDE, it's probably a good idea to somehow apply our code style settings. If you can save us those extra clicks cleaning up your code after accepting it, it would be great and surely improve overall code history. Not applying it is no reason to reject your merge request.
