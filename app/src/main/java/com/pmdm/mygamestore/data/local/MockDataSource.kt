package com.pmdm.mygamestore.data.local

import com.pmdm.mygamestore.domain.model.EsrbRating
import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.GameCategory
import com.pmdm.mygamestore.domain.model.Genre
import com.pmdm.mygamestore.domain.model.Platform
import com.pmdm.mygamestore.domain.model.PlatformEnum
import com.pmdm.mygamestore.domain.model.Publisher
import com.pmdm.mygamestore.domain.model.Screenshot
import com.pmdm.mygamestore.domain.model.Store
import com.pmdm.mygamestore.domain.model.Tag

/**
 * MockDataSource es un objeto que simula una fuente de datos para una aplicación centrada en videojuegos.
 * Sirve como recurso inicial para proporcionar información estática relacionada con géneros, plataformas,
 * editores, tiendas, etiquetas y juegos.
 *
 * Datos proveídos:
 * - `genres`: Lista de géneros populares de videojuegos, definidos por un identificador único, nombre y slug.
 * - `platforms`: Lista de plataformas disponibles para videojuegos, con su respectivo identificador, nombre y slug.
 * - `publishers`: Lista de editores de videojuegos reconocidos, identificados por un ID, nombre y slug.
 * - `stores`: Lista de tiendas en las que se pueden adquirir juegos o contenido relacionado, con su correspondiente identificador, nombre y slug.
 * - `tags`: Lista de etiquetas (tags) relevantes para clasificar los videojuegos, definidas por ID, nombre y slug.
 * - `games`: Lista de detalles de juegos predefinidos, incluyendo información como título, descripción, precio, calificación, fecha de lanzamiento,
 *  plataformas soportadas, géneros relacionados, tiendas donde están disponibles, etiquetas asociadas, capturas de pantalla, entre otros atributos específicos
 * .
 */
object MockDataSource {

    val genres = listOf(
        Genre(id = 4, name = "Action", slug = "action"),
        Genre(id = 51, name = "Indie", slug = "indie"),
        Genre(id = 3, name = "Adventure", slug = "adventure"),
        Genre(id = 5, name = "RPG", slug = "role-playing-games-rpg"),
        Genre(id = 10, name = "Strategy", slug = "strategy"),
        Genre(id = 2, name = "Shooter", slug = "shooter"),
        Genre(id = 40, name = "Casual", slug = "casual"),
        Genre(id = 14, name = "Simulation", slug = "simulation"),
        Genre(id = 7, name = "Puzzle", slug = "puzzle"),
        Genre(id = 11, name = "Arcade", slug = "arcade"),
        Genre(id = 83, name = "Platformer", slug = "platformer"),
        Genre(id = 59, name = "Massively Multiplayer", slug = "massively-multiplayer"),
        Genre(id = 1, name = "Racing", slug = "racing"),
        Genre(id = 15, name = "Sports", slug = "sports"),
        Genre(id = 6, name = "Fighting", slug = "fighting"),
        Genre(id = 19, name = "Family", slug = "family"),
        Genre(id = 28, name = "Board Games", slug = "board-games"),
        Genre(id = 17, name = "Card", slug = "card"),
        Genre(id = 34, name = "Educational", slug = "educational")
    )

    val platforms = listOf(
        Platform(id = 4, name = "PC", slug = "pc"),
        Platform(id = 187, name = "PlayStation 5", slug = "playstation5"),
        Platform(id = 18, name = "PlayStation 4", slug = "playstation4"),
        Platform(id = 1, name = "PlayStation 3", slug = "playstation3"),
        Platform(id = 186, name = "Xbox Series S/X", slug = "xbox-series-x"),
        Platform(id = 14, name = "Xbox One", slug = "xbox-one"),
        Platform(id = 80, name = "Xbox 360", slug = "xbox360"),
        Platform(id = 7, name = "Nintendo Switch", slug = "nintendo-switch"),
        Platform(id = 3, name = "iOS", slug = "ios"),
        Platform(id = 21, name = "Android", slug = "android")
    )

    val publishers = listOf(
        Publisher(id = 354, name = "Electronic Arts", slug = "electronic-arts"),
        Publisher(id = 3408, name = "Ubisoft Entertainment", slug = "ubisoft-entertainment"),
        Publisher(id = 339, name = "Bethesda Softworks", slug = "bethesda-softworks"),
        Publisher(id = 3399, name = "Rockstar Games", slug = "rockstar-games"),
        Publisher(id = 33, name = "Warner Bros. Interactive", slug = "warner-bros-interactive"),
        Publisher(id = 209, name = "Sony Interactive Entertainment", slug = "sony-interactive-entertainment"),
        Publisher(id = 45, name = "Microsoft Xbox Game Studios", slug = "microsoft-xbox-game-studios"),
        Publisher(id = 16257, name = "Nintendo", slug = "nintendo"),
        Publisher(id = 9191, name = "Sega", slug = "sega-2"),
        Publisher(id = 3390, name = "Square Enix", slug = "square-enix"),
        Publisher(id = 347, name = "Capcom", slug = "capcom"),
        Publisher(id = 345, name = "Activision Blizzard", slug = "activision-blizzard"),
        Publisher(id = 25, name = "2K Games", slug = "2k-games"),
        Publisher(id = 208, name = "Bandai Namco Entertainment", slug = "bandai-namco-entertainment"),
        Publisher(id = 3410, name = "Deep Silver", slug = "deep-silver")
    )

    val stores = listOf(
        Store(id = 1, name = "Steam", slug = "steam"),
        Store(id = 3, name = "PlayStation Store", slug = "playstation-store"),
        Store(id = 2, name = "Xbox Store", slug = "xbox-store"),
        Store(id = 4, name = "App Store", slug = "apple-appstore"),
        Store(id = 8, name = "Google Play", slug = "google-play"),
        Store(id = 5, name = "GOG", slug = "gog"),
        Store(id = 6, name = "Nintendo Store", slug = "nintendo"),
        Store(id = 7, name = "Xbox 360 Store", slug = "xbox360"),
        Store(id = 9, name = "itch.io", slug = "itch"),
        Store(id = 11, name = "Epic Games", slug = "epic-games")
    )

    val tags = listOf(
        Tag(id = 31, name = "Singleplayer", slug = "singleplayer"),
        Tag(id = 40836, name = "Full controller support", slug = "full-controller-support"),
        Tag(id = 7, name = "Multiplayer", slug = "multiplayer"),
        Tag(id = 40847, name = "Steam Achievements", slug = "steam-achievements"),
        Tag(id = 13, name = "Atmospheric", slug = "atmospheric"),
        Tag(id = 40849, name = "Steam Cloud", slug = "steam-cloud"),
        Tag(id = 42, name = "Great Soundtrack", slug = "great-soundtrack"),
        Tag(id = 24, name = "RPG", slug = "rpg"),
        Tag(id = 18, name = "Co-op", slug = "co-op"),
        Tag(id = 118, name = "Story Rich", slug = "story-rich"),
        Tag(id = 411, name = "Cooperative", slug = "cooperative"),
        Tag(id = 8, name = "First-Person", slug = "first-person"),
        Tag(id = 32, name = "Sci-fi", slug = "sci-fi"),
        Tag(id = 149, name = "Third Person", slug = "third-person"),
        Tag(id = 4, name = "Funny", slug = "funny"),
        Tag(id = 37, name = "Sandbox", slug = "sandbox"),
        Tag(id = 123, name = "Comedy", slug = "comedy"),
        Tag(id = 64, name = "Fantasy", slug = "fantasy"),
        Tag(id = 147, name = "2D", slug = "2d")
    )

    val games = listOf(
        Game(
            id = 998492,
            slug = "system-shock-2-25th-anniversary-remaster",
            title = "System Shock 2: 25th Anniversary Remaster",
            description = "From FromSoftware and George R.R. Martin comes a dark fantasy epic. Explore a vast interconnected world filled with danger and mystery in this groundbreaking action RPG adventure.",
            imageUrl = "https://media.rawg.io/media/screenshots/95d/95df624f84b7bca99b56da01aeea4789.jpg",
            price = 29.99,
            rating = 4.62,
            releaseDate = "2025-06-25",
            category = GameCategory.ACTION,
            platforms = listOf(platforms[0]), // PC
            genres = listOf(genres[2], genres[3], genres[4]), // Adventure, Action, RPG
            stores = listOf(stores[0], stores[5]), // Steam, GOG
            tags = listOf(tags[0], tags[4], tags[6], tags[7], tags[17]),
            screenshots = listOf(
                Screenshot(
                    -1,
                    "https://media.rawg.io/media/screenshots/95d/95df624f84b7bca99b56da01aeea4789.jpg"
                ),
                Screenshot(
                    4167067,
                    "https://media.rawg.io/media/screenshots/2f6/2f6816c288af8ed154a0d0d0e9b21e4d.jpg"
                ),
                Screenshot(
                    4167068,
                    "https://media.rawg.io/media/screenshots/965/9650e2922d2c4d5efe3c4bf97c9f0c81.jpg"
                )
            ),
            metacritic = null,
            playtime = 1,
            ratingsCount = 8,
            esrbRating = EsrbRating(4, "Mature", "mature")
        ),
        Game(
            id = 1002847,
            slug = "geoguessr",
            title = "GeoGuessr",
            description = "Experience geography exploration like never before. Test your knowledge of the world in this engaging puzzle game that challenges you to identify locations from around the globe.",
            imageUrl = "https://media.rawg.io/media/screenshots/252/252fea2a340c8f398ca56ddb7649a20e.jpg",
            price = 9.99,
            rating = 4.5,
            releaseDate = "2025-05-08",
            category = GameCategory.PUZZLE,
            platforms = listOf(platforms[0]), // PC
            genres = listOf(genres[6], genres[3]), // Casual, Action
            stores = listOf(stores[0]), // Steam
            tags = listOf(tags[6], tags[3], tags[9], tags[5], tags[8]),
            screenshots = listOf(
                Screenshot(
                    -1,
                    "https://media.rawg.io/media/screenshots/252/252fea2a340c8f398ca56ddb7649a20e.jpg"
                ),
                Screenshot(
                    4199990,
                    "https://media.rawg.io/media/screenshots/0a0/0a005561eeb8a9e178529ccca3d623f7.jpg"
                )
            ),
            metacritic = null,
            playtime = 2,
            ratingsCount = 6,
            esrbRating = null
        ),
        Game(
            id = 983210,
            slug = "clair-obscur-expedition-33",
            title = "Clair Obscur: Expedition 33",
            description = "Embark on a mysterious expedition in this atmospheric RPG adventure. Uncover secrets, battle formidable foes, and shape the fate of your journey with meaningful choices.",
            imageUrl = "https://media.rawg.io/media/games/466/4667f17fdee9ebbcea2049e54f8e2b96.jpg",
            price = 49.99,
            rating = 4.5,
            releaseDate = "2025-04-24",
            category = GameCategory.RPG,
            platforms = listOf(platforms[0], platforms[1], platforms[5]), // PC, PS5, Xbox Series
            genres = listOf(genres[4]), // RPG
            stores = listOf(stores[0], stores[2], stores[1], stores[9]), // Steam, PS Store, Xbox Store, Epic
            tags = listOf(tags[0], tags[4], tags[7], tags[15], tags[10]),
            screenshots = listOf(
                Screenshot(
                    -1,
                    "https://media.rawg.io/media/games/466/4667f17fdee9ebbcea2049e54f8e2b96.jpg"
                ),
                Screenshot(
                    4052553,
                    "https://media.rawg.io/media/screenshots/1e4/1e4dcb884c03242be7dab30fad77d7b2.jpg"
                ),
                Screenshot(
                    4161703,
                    "https://media.rawg.io/media/screenshots/22c/22c350e84e9f320b389cdabf0c213f7f.jpg"
                )
            ),
            metacritic = null,
            playtime = 25,
            ratingsCount = 355,
            esrbRating = null
        ),
        Game(
            id = 994603,
            slug = "split-fiction",
            title = "Split Fiction",
            description = "A unique cooperative adventure from the creators of It Takes Two. Experience a story-rich journey that seamlessly blends action and adventure in innovative ways.",
            imageUrl = "https://media.rawg.io/media/games/02a/02ac22b3b90717dabaa535640c38534c.jpg",
            price = 39.99,
            rating = 4.47,
            releaseDate = "2025-03-06",
            category = GameCategory.ADVENTURE,
            platforms = listOf(platforms[0], platforms[1], platforms[5]), // PC, PS5, Xbox Series
            genres = listOf(genres[2], genres[3]), // Adventure, Action
            stores = listOf(stores[0], stores[2], stores[9]), // Steam, PS Store, Epic
            tags = listOf(tags[6], tags[10], tags[7], tags[1], tags[9]),
            screenshots = listOf(
                Screenshot(
                    -1,
                    "https://media.rawg.io/media/games/02a/02ac22b3b90717dabaa535640c38534c.jpg"
                ),
                Screenshot(
                    4137937,
                    "https://media.rawg.io/media/screenshots/dc1/dc10dd68a2e7472b7ed40feda3a16c54.jpg"
                ),
                Screenshot(
                    4137938,
                    "https://media.rawg.io/media/screenshots/ea9/ea9e37dc1b36db3401a6b308d766198b.jpg"
                )
            ),
            metacritic = null,
            playtime = 3,
            ratingsCount = 189,
            esrbRating = null
        ),
        Game(
            id = 801122,
            slug = "the-alters",
            title = "The Alters",
            description = "A thought-provoking sci-fi strategy adventure. Make critical decisions, build your base, and explore a mysterious alien world while managing alternate versions of yourself.",
            imageUrl = "https://media.rawg.io/media/games/5f8/5f8b6e0bfdf4efd5e2b9231df8721de0.jpg",
            price = 34.99,
            rating = 4.36,
            releaseDate = "2025-06-13",
            category = GameCategory.STRATEGY,
            platforms = listOf(platforms[0], platforms[1], platforms[5]), // PC, PS5, Xbox Series
            genres = listOf(genres[2]), // Adventure
            stores = listOf(stores[0], stores[2], stores[1], stores[5]), // Steam, PS Store, Xbox Store, GOG
            tags = listOf(tags[0], tags[7], tags[4], tags[16], tags[13]),
            screenshots = listOf(
                Screenshot(
                    -1,
                    "https://media.rawg.io/media/games/5f8/5f8b6e0bfdf4efd5e2b9231df8721de0.jpg"
                ),
                Screenshot(
                    4020091,
                    "https://media.rawg.io/media/screenshots/eda/eda60996530ac10e728ebb894c2b4406.jpg"
                ),
                Screenshot(
                    4020092,
                    "https://media.rawg.io/media/screenshots/4a9/4a98d51ad9c428e397954b03412d3433.jpg"
                )
            ),
            metacritic = null,
            playtime = 7,
            ratingsCount = 68,
            esrbRating = null
        ),
        Game(
            id = 292844,
            slug = "hollow-knight-silksong",
            title = "Hollow Knight: Silksong",
            description = "Descend into a haunting new kingdom as Hornet in this eagerly awaited sequel. Master acrobatic combat, explore vast interconnected worlds, and uncover the secrets of Silksong.",
            imageUrl = "https://media.rawg.io/media/games/27c/27cd8b7dead05a870f8a514a9a1915ad.jpg",
            price = 34.99,
            rating = 4.35,
            releaseDate = "2025-09-04",
            category = GameCategory.ACTION,
            platforms = listOf(platforms[0], platforms[1], platforms[3], platforms[7], platforms[4], platforms[5]), // PC, PS5, PS4, Switch, macOS, Xbox
            genres = listOf(genres[0], genres[10], genres[2], genres[3]), // Indie, Platformer, Adventure, Action
            stores = listOf(stores[0], stores[5]), // Steam, GOG
            tags = listOf(tags[0], tags[4], tags[7], tags[2], tags[5]),
            screenshots = listOf(
                Screenshot(
                    -1,
                    "https://media.rawg.io/media/games/27c/27cd8b7dead05a870f8a514a9a1915ad.jpg"
                ),
                Screenshot(
                    1837511,
                    "https://media.rawg.io/media/screenshots/9f9/9f9a0edd1478facde5209abe4000c015.jpg"
                ),
                Screenshot(
                    1837512,
                    "https://media.rawg.io/media/screenshots/3f3/3f35cf2130d1d8763ee45dc77ce843b2.jpg"
                )
            ),
            metacritic = null,
            playtime = 22,
            ratingsCount = 202,
            esrbRating = null
        ),
        Game(
            id = 983381,
            slug = "doom-the-dark-ages",
            title = "Doom: The Dark Ages",
            description = "Return to medieval times in this brutal prequel to the legendary FPS series. Unleash hell with devastating weapons and face hordes of demons in an epic dark fantasy setting.",
            imageUrl = "https://media.rawg.io/media/games/018/01897340a06b9ed8e92ed1cc1b1eecb9.jpg",
            price = 59.99,
            rating = 4.35,
            releaseDate = "2025-05-15",
            category = GameCategory.ACTION,
            platforms = listOf(platforms[0], platforms[1], platforms[5]), // PC, PS5, Xbox Series
            genres = listOf(genres[3]), // Action
            stores = listOf(stores[0], stores[2], stores[1]), // Steam, PS Store, Xbox Store
            tags = listOf(tags[0], tags[4], tags[7], tags[11], tags[12]),
            screenshots = listOf(
                Screenshot(
                    -1,
                    "https://media.rawg.io/media/games/018/01897340a06b9ed8e92ed1cc1b1eecb9.jpg"
                ),
                Screenshot(
                    4053759,
                    "https://media.rawg.io/media/screenshots/8f1/8f19f2c0d824633e97bfe32117a8cdd1.jpg"
                ),
                Screenshot(
                    4053760,
                    "https://media.rawg.io/media/screenshots/5b4/5b47eb35a77ed3dbdc0f7854268666a7.jpg"
                )
            ),
            metacritic = null,
            playtime = 16,
            ratingsCount = 68,
            esrbRating = EsrbRating(4, "Mature", "mature")
        ),
        Game(
            id = 1008166,
            slug = "ball-x-pit",
            title = "Ball x Pit",
            description = "A fast-paced action roguelike where you build your arsenal and craft your base while battling through waves of enemies. Pixel art graphics meet addictive gameplay in this indie gem.",
            imageUrl = "https://media.rawg.io/media/games/798/798705b4f25e958e4ab8edf570e215f8.jpg",
            price = 14.99,
            rating = 4.38,
            releaseDate = "2025-10-15",
            category = GameCategory.ACTION,
            platforms = listOf(platforms[0], platforms[7], platforms[4]), // PC, Switch, macOS
            genres = listOf(genres[0], genres[3]), // Indie, Action
            stores = listOf(stores[0]), // Steam
            tags = listOf(tags[4], tags[7], tags[2], tags[1], tags[5]),
            screenshots = listOf(
                Screenshot(
                    -1,
                    "https://media.rawg.io/media/games/798/798705b4f25e958e4ab8edf570e215f8.jpg"
                ),
                Screenshot(
                    4271792,
                    "https://media.rawg.io/media/screenshots/592/592546d38aa4b289309d9d506c2c59e4.jpg"
                ),
                Screenshot(
                    4271793,
                    "https://media.rawg.io/media/screenshots/f5d/f5dcf68bf10a1035375c362858c0195b.jpg"
                )
            ),
            metacritic = null,
            playtime = 6,
            ratingsCount = 32,
            esrbRating = null
        ),
        Game(
            id = 980502,
            slug = "kingdom-come-deliverance-ii",
            title = "Kingdom Come: Deliverance II",
            description = "Return to medieval Bohemia in this realistic RPG sequel. Experience authentic historical combat, make impactful choices, and explore a vast open world filled with detail.",
            imageUrl = "https://media.rawg.io/media/games/d84/d842fec4ae7bbd782d330f678c980f7f.jpg",
            price = 59.99,
            rating = 4.4,
            releaseDate = "2025-02-03",
            category = GameCategory.RPG,
            platforms = listOf(platforms[0], platforms[1], platforms[5]), // PC, PS5, Xbox Series
            genres = listOf(genres[2], genres[3], genres[4]), // Adventure, Action, RPG
            stores = listOf(stores[0], stores[5]), // Steam, GOG
            tags = listOf(tags[0], tags[4], tags[7], tags[2], tags[17]),
            screenshots = listOf(
                Screenshot(
                    -1,
                    "https://media.rawg.io/media/games/d84/d842fec4ae7bbd782d330f678c980f7f.jpg"
                ),
                Screenshot(
                    4152948,
                    "https://media.rawg.io/media/screenshots/9a8/9a8ecc33989862e3be29132189c3f7f6.jpg"
                ),
                Screenshot(
                    4152949,
                    "https://media.rawg.io/media/screenshots/741/741f7625ce8adfe138b43608f1e8e01c.jpg"
                )
            ),
            metacritic = null,
            playtime = 26,
            ratingsCount = 127,
            esrbRating = EsrbRating(5, "Adults Only", "adults-only")
        ),
        Game(
            id = 891238,
            slug = "hades-ii",
            title = "Hades II",
            description = "The acclaimed roguelike returns! Battle through the Underworld as Melinoë, wielding magic and might. Stunning hand-drawn art, addictive gameplay, and rich storytelling await.",
            imageUrl = "https://media.rawg.io/media/games/8fd/8fd2e8317849fd265ad8781c324d4ec2.jpg",
            price = 29.99,
            rating = 4.32,
            releaseDate = "2025-09-25",
            category = GameCategory.ACTION,
            platforms = listOf(platforms[0], platforms[7]), // PC, Switch
            genres = listOf(genres[0], genres[2], genres[3], genres[4]), // Indie, Adventure, Action, RPG
            stores = listOf(stores[0], stores[9]), // Steam, Epic
            tags = listOf(tags[0], tags[4], tags[7], tags[2], tags[3]),
            screenshots = listOf(
                Screenshot(
                    -1,
                    "https://media.rawg.io/media/games/8fd/8fd2e8317849fd265ad8781c324d4ec2.jpg"
                ),
                Screenshot(
                    3681385,
                    "https://media.rawg.io/media/screenshots/59e/59e9ba1215b11e43ad64f363bfb7f65b.jpg"
                ),
                Screenshot(
                    3681386,
                    "https://media.rawg.io/media/screenshots/e93/e93aa58a1bf6f628507121a206ef856d.jpg"
                )
            ),
            metacritic = null,
            playtime = 7,
            ratingsCount = 98,
            esrbRating = null
        )
    )
}
