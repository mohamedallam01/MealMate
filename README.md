# MealMate

## Brief Description
MealMate is an Android mobile application designed to assist users in planning their weekly meals. With features like viewing categories, suggesting meals, searching for specific meals using various criteria, and saving favorite meals for offline browsing,MealMate aims to streamline meal planning and make it more convenient for users. The application utilizes the ((https://themealdb.com/api.php)) to provide a wide range of meal options.

## Project Features
- **Splash Screen**: Displays a splash screen with animation using Lottie.
- 
  <div style="text-align: center;">
    <img src="https://github.com/mohamedallam01/MealMate/blob/master/screenshots/splash%20screen.jpg?raw=true" width="30%">
</div>


  - **Authentication**:
  - Simple login and signup options, including social networking authentication (Firebase authentication).
  - Registered users can access their archived data upon successful login without needing to log in again (Local SharedPreferences, Firebase).
  - Guest mode allows users to access basic features like viewing categories, searching, and viewing the meal of the day.

<div style="display:flex; justify-content:space-between;">
    <img src="https://github.com/mohamedallam01/MealMate/blob/master/screenshots/register_login.jpg?raw=true" width="30%">
    <img src="https://github.com/mohamedallam01/MealMate/blob/master/screenshots/registeration%20form.jpg?raw=true" width="30%">
    <img src="https://github.com/mohamedallam01/MealMate/blob/master/screenshots/login.jpg?raw=true" width="30%">
</div>

- **Meal of the Day**: Users can view a randomly selected meal for inspiration and discover National Meals according to the country they live in
  <div style="display:flex; justify-content:center">
    <img src="https://github.com/mohamedallam01/MealMate/blob/master/screenshots/home.jpg?raw=true" width="30%">
</div>

- **Search Functionality**: Users can search for meals based on country, ingredient, or category.
  <div style="display:flex; justify-content:center">
    <img src="https://github.com/mohamedallam01/MealMate/blob/master/screenshots/search.jpg?raw=true" width="30%">
</div>

- **Favorite Meals**: Users can add meals to favorites or remove them. (Local storage using Room, Firebase not allowed)
  <div style="display:flex; justify-content:center">
    <img src="https://github.com/mohamedallam01/MealMate/blob/master/screenshots/favorite.jpg?raw=true" width="30%">
</div>

- **Weekly Meal Planning**: Users can view and add meals for the current week.
  <div style="display:flex; justify-content:center">
     <img src="https://github.com/mohamedallam01/MealMate/blob/master/screenshots/days.jpg?raw=true" width="30%">
    <img src="https://github.com/mohamedallam01/MealMate/blob/master/screenshots/week%20plan.jpg?raw=true" width="30%">
</div>

- **Meal Details**: Provides detailed information about each meal, including name, image, origin country, ingredients, preparation steps, and an embedded video.
   <div style="display:flex; justify-content:space-between;">
    <img src="https://github.com/mohamedallam01/MealMate/blob/master/screenshots/details.jpg?raw=true" width="30%">
</div>




## Installation
To install MealMate on your device:

1. Download the app file from the ((https://github.com/mohamedallam01/MealMate.git)).


## Usage
1. Launch the MealMate app on your device.
2. Log in or sign up to access personalized features or use the app in guest mode.
3. Explore categories, search for meals, and plan your weekly meals.
4. Add your favorite meals for quick access.
5. Enjoy the convenience of meal planning with Meal Planner!

## Contributing
Contributions to the Meal Planner project are welcome! If you have any suggestions, feature requests, or bug reports, please open an issue or submit a pull request on the [GitHub repository](https://github.com/mohamedallam01/MealMate.git).

## License
This project is licensed under the ([ITI](https://iti.gov.eg/)). Feel free to use, modify, and distribute the code for your own purposes.
