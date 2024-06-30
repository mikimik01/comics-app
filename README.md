# Marvel Comics App

This is a Marvel Comics application built with Android, Kotlin, Retrofit, and other modern libraries. The app displays a list of Marvel comics, allows searching for comics, and shows detailed information about each comic.

## Features

- Display a list of Marvel comics
- Search for specific comics
- View detailed information about a selected comic
- Pagination for loading more comics
- Full-screen splash screen until data is loaded

## Screenshots

<p align="center">
  <img src="https://github.com/mikimik01/comics-app/assets/51535459/4ea1ff7c-0215-4997-92a5-078042ffe376" width=30% height=30%>
  <img src="https://github.com/mikimik01/comics-app/assets/51535459/9a814ef9-caca-4e04-a173-8c8eb1941472" width=30% height=30%>
  <img src="https://github.com/mikimik01/comics-app/assets/51535459/8f768505-12c7-44a3-a9da-a5c78199110e" width=30% height=30%>
</p>


## Project Structure

### Data
- **ComicData:** Contains all data classes related to comics.
- **MarvelApi:** Handles the connection to the Marvel API.
- **MarvelApiService:** Manages query creation.

### UI
- **ComicDetailFragment:** Handles displaying comic details.
- **HomeFragment:** Manages the home layout.
- **SearchFragment:** Manages the search layout.
- **ComicsAdapter:** Handles items and generally the listing layout.

### ViewModel
- **HomeViewModel:** Retrieves all comics from the Marvel API.
- **SearchViewModel:** Retrieves searched comics from the Marvel API.

### Activities
- **MainActivity:** Switches between the three fragments described above.
- **SplashActivity:** Displays splash art while the app is loading.


## Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/mikimik01/comics-app.git
