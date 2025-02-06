UiExample

Overview

UiExample is an Android project showcasing smooth UI animations, specifically for hiding and showing the Bottom Navigation Bar and Top App Bar while scrolling. The project is built using Jetpack Compose and Compose Navigation with a focus on UI interactions.

Features

Animated Bottom Navigation & Top App Bar: Automatically hide when scrolling down and show when scrolling up.

Jetpack Compose Navigation: Implementing a multi-screen UI with Compose's navigation component.

Smooth UI/UX: Enhancing user experience with fluid animations and transitions.

Custom Animations: Seamless UI animations for a modern look and feel.

Technologies Used

Kotlin: Primary programming language.

Jetpack Compose: Modern UI toolkit for declarative UI development.

Compose Navigation: Handling navigation between screens.

Installation & Setup

Clone the repository:

git clone https://github.com/mohamedebrahem13/UiExample.git

Open the project in Android Studio (preferably Giraffe or later).

Sync the Gradle files and build the project.

Run the app on an emulator or a real device.

Project Structure

UiExample/
│── app/
│   ├── ui/
│   │   ├── components/    # Reusable UI components
│   │   ├── screens/       # Individual screens with Compose
│   │   ├── navigation/    # Navigation setup
│   ├── utils/            # Utility functions

How It Works

The Bottom Navigation Bar and Top App Bar visibility are controlled based on the user's scroll direction.

A LazyColumn is used for smooth scrolling.

Compose's Modifier & State are leveraged to observe and animate the UI components.

Navigation between screens is handled using Compose Navigation.

Future Improvements

Implement custom animations for transitions between screens.

Introduce gesture-based navigation.

Contributions

Feel free to contribute by submitting a pull request or reporting issues.

License

This project is open-source under the MIT License.
