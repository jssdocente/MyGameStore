package com.pmdm.mygamestore.data.local

import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.GameCategory
import com.pmdm.mygamestore.domain.model.Genre
import com.pmdm.mygamestore.domain.model.Platform
import com.pmdm.mygamestore.domain.model.PlatformEnum
import com.pmdm.mygamestore.domain.model.Publisher
import com.pmdm.mygamestore.domain.model.Store
import com.pmdm.mygamestore.domain.model.Tag

/**
 *  Fuente de datos mock para desarrollo
 *
 * RESPONSABILIDAD:
 * - Proveer datos de prueba para desarrollo sin depender de API
 * - Simular respuesta de una base de datos o API
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
            id = 1,
            title = "The Witcher 3: Wild Hunt",
            description = "An epic open-world RPG adventure set in a fantasy universe. Hunt monsters, make choices that matter, and explore a vast world filled with quests.",
            imageUrl = "https://picsum.photos/400/600?random=1",
            price = 39.99,
            rating = 4.8,
            releaseDate = "2024-01-15",
            category = GameCategory.RPG,
            platform = PlatformEnum.PC,
            genres = listOf("RPG", "Open World", "Fantasy")
        ),
        Game(
            id = 2,
            title = "God of War Ragnarök",
            description = "Continue Kratos and Atreus' Norse adventure in this breathtaking sequel. Epic battles, emotional storytelling, and stunning visuals await.",
            imageUrl = "https://picsum.photos/400/600?random=2",
            price = 59.99,
            rating = 4.9,
            releaseDate = "2024-02-01",
            category = GameCategory.ACTION,
            platform = PlatformEnum.PLAYSTATION,
            genres = listOf("Action", "Adventure", "Mythology")
        ),
        Game(
            id = 3,
            title = "Halo Infinite",
            description = "Master Chief returns in this epic FPS adventure. Experience the next chapter of the legendary Halo saga with new gameplay and multiplayer.",
            imageUrl = "https://picsum.photos/400/600?random=3",
            price = 49.99,
            rating = 4.5,
            releaseDate = "2023-12-10",
            category = GameCategory.ACTION,
            platform = PlatformEnum.XBOX,
            genres = listOf("FPS", "Sci-Fi", "Multiplayer")
        ),
        Game(
            id = 4,
            title = "The Legend of Zelda: TOTK",
            description = "Explore Hyrule like never before in this groundbreaking adventure. Discover new abilities, solve puzzles, and uncover ancient secrets.",
            imageUrl = "https://picsum.photos/400/600?random=4",
            price = 59.99,
            rating = 5.0,
            releaseDate = "2024-01-20",
            category = GameCategory.ADVENTURE,
            platform = PlatformEnum.NINTENDO,
            genres = listOf("Adventure", "Puzzle", "Open World")
        ),
        Game(
            id = 5,
            title = "Stardew Valley",
            description = "Build your dream farm in this relaxing and addictive simulation. Grow crops, raise animals, go fishing, and become part of the community.",
            imageUrl = "https://picsum.photos/400/600?random=5",
            price = 14.99,
            rating = 4.7,
            releaseDate = "2023-11-05",
            category = GameCategory.SIMULATION,
            platform = PlatformEnum.MOBILE,
            genres = listOf("Simulation", "Farming", "Indie")
        ),
        Game(
            id = 6,
            title = "FIFA 24",
            description = "The ultimate football experience with realistic gameplay, updated rosters, and exciting new game modes for solo and multiplayer.",
            imageUrl = "https://picsum.photos/400/600?random=6",
            price = 69.99,
            rating = 4.2,
            releaseDate = "2024-02-10",
            category = GameCategory.SPORTS,
            platform = PlatformEnum.PC,
            genres = listOf("Sports", "Simulation", "Multiplayer")
        ),
        Game(
            id = 7,
            title = "Civilization VI",
            description = "Build an empire to stand the test of time. Lead your civilization from the Stone Age to the Information Age in this turn-based strategy masterpiece.",
            imageUrl = "https://picsum.photos/400/600?random=7",
            price = 29.99,
            rating = 4.6,
            releaseDate = "2023-10-20",
            category = GameCategory.STRATEGY,
            platform = PlatformEnum.PC,
            genres = listOf("Strategy", "Turn-Based", "Historical")
        ),
        Game(
            id = 8,
            title = "Tetris Effect",
            description = "Experience Tetris like never before with stunning visuals and an incredible soundtrack that reacts to your gameplay in real-time.",
            imageUrl = "https://picsum.photos/400/600?random=8",
            price = 19.99,
            rating = 4.4,
            releaseDate = "2024-01-05",
            category = GameCategory.PUZZLE,
            platform = PlatformEnum.MOBILE,
            genres = listOf("Puzzle", "Music", "Casual")
        ),
        Game(
            id = 9,
            title = "Elden Ring",
            description = "From FromSoftware and George R.R. Martin comes a dark fantasy epic. Explore a vast interconnected world filled with danger and mystery.",
            imageUrl = "https://picsum.photos/400/600?random=9",
            price = 59.99,
            rating = 4.9,
            releaseDate = "2024-01-25",
            category = GameCategory.RPG,
            platform = PlatformEnum.PLAYSTATION,
            genres = listOf("RPG", "Souls-like", "Dark Fantasy")
        ),
        Game(
            id = 10,
            title = "Forza Horizon 5",
            description = "Open-world racing in beautiful Mexico. Drive hundreds of the world's greatest cars through stunning environments and dynamic seasons.",
            imageUrl = "https://picsum.photos/400/600?random=10",
            price = 49.99,
            rating = 4.7,
            releaseDate = "2023-12-15",
            category = GameCategory.SPORTS,
            platform = PlatformEnum.XBOX,
            genres = listOf("Racing", "Open World", "Arcade")
        ),
        Game(
            id = 11,
            title = "Minecraft",
            description = "Create and explore infinite worlds. Build anything you can imagine in this blocky sandbox adventure that has captured millions of players.",
            imageUrl = "https://picsum.photos/400/600?random=11",
            price = 26.95,
            rating = 4.8,
            releaseDate = "2023-11-12",
            category = GameCategory.SIMULATION,
            platform = PlatformEnum.MOBILE,
            genres = listOf("Sandbox", "Survival", "Creative")
        ),
        Game(
            id = 12,
            title = "Call of Duty: MW3",
            description = "The iconic FPS returns with intense multiplayer action and a gripping campaign. Join the fight in the latest Modern Warfare installment.",
            imageUrl = "https://picsum.photos/400/600?random=12",
            price = 69.99,
            rating = 4.3,
            releaseDate = "2024-02-05",
            category = GameCategory.ACTION,
            platform = PlatformEnum.PC,
            genres = listOf("FPS", "Military", "Multiplayer")
        )
    )
}
