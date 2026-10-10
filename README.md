This is an old project reuploaded.

# Catalist

Android app for browsing cat breeds, built with Jetpack Compose. Data comes from [TheCatAPI](https://thecatapi.com/).

## Features

- **Breed list**: searchable list with image, alt names, short description (max 250 chars) and temperament chips. Cached in a local Room DB.
- **Breed details**: origin, weight range, temperament, behaviour traits (affection, grooming, health, intelligence, child-friendliness), "Open wiki" button.
- **Photo album**: grid of breed photos, tap one to open a swipeable full-screen gallery.
- **Profile / login**: first launch asks for name, username and email. Stored locally with DataStore; later launches skip straight to the breed list.
- **Quiz** and **Leaderboard** tabs: placeholders, not implemented yet.

## Tech stack

| Area | Library |
|---|---|
| UI | Jetpack Compose, Material 3, Navigation Compose |
| Architecture | MVVM with state / event / effect contracts per screen |
| DI | Hilt (via KSP) |
| Networking | Retrofit, OkHttp, kotlinx.serialization |
| Local DB | Room (`rma.db`) |
| Profile storage | DataStore (Okio serializer, JSON file `profile.json`) |
| Images | Coil 3 |

Min SDK 24, target/compile SDK 35, Kotlin 2.1.20, Java 17.

## Project structure

```
app/src/main/java/com/example/catalist/
├── api/           Retrofit interface, API models, OkHttp/Retrofit Hilt module
├── db/            Room database, DAO, entities, type converter
├── repos/         CatApiRepoImpl (API + DB access)
├── breedlist/     Breed list screen + ViewModel
├── breeddetails/  Breed details screen + ViewModel
├── breedalbum/    Photo grid + gallery pager
├── profile/       Login form, profile screen, DataStore storage
├── navigation/    StartNavigation (login vs app), AppNavigation (bottom tabs), CatNavigation (breed screens)
├── commonui/      Shared composables (search bar, top bar, image, loaders)
├── quiz/, leaderboard/   Stubs
└── ui/theme/      Compose theme
```

Navigation is layered: `StartNavigation` picks login or main app, `AppNavigation` hosts the bottom tabs, and `CatsNavigation` handles list → details → album → gallery.

## Build and run

1. Open the project in Android Studio (Ladybug or newer, needs AGP 8.9.1).
2. Let it regenerate `local.properties` (the committed one points to a Windows SDK path).
3. Get a free key at https://thecatapi.com/signup and add it to `local.properties` (gitignored):
   ```
   CAT_API_KEY=your-key-here
   ```
4. Run the `app` configuration on a device or emulator (API 24+).

Needs internet access on first launch to fill the cache.

## Known limitations

- Quiz and Leaderboard screens are not implemented.
- The Profile tab shows hardcoded placeholder data, not the saved profile.
- UI strings are hardcoded, mixed Serbian/English.
