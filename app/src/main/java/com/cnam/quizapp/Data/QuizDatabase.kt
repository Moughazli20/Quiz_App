package com.cnam.quizapp.Data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cnam.quizapp.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Question::class, Category::class], version = 1)
@TypeConverters(Converters::class)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: QuizDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): QuizDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuizDatabase::class.java,
                    "quiz_database"
                )
                    .addCallback(QuizDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class QuizDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        Log.d("QuizDatabase", "Populating database on creation")
                        populateDatabase(database.categoryDao(), database.questionDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(categoryDao: CategoryDao, questionDao: QuestionDao) {
            val categories = listOf(
                Category("Science"),
                Category("Histoire"),
                Category("Géographie"),
                Category("Technologie"),
                Category("Divertissement"),
                Category("Animaux")
            )
            categoryDao.insertAll(categories)
            Log.d("QuizDatabase", "Inserted categories")

            val questions = mutableListOf<Question>()

            // Science
            questions.add(
                Question(
                    category = "Science",
                    text = "Quelle est la formule chimique de l'eau ?",
                    correctAnswer = "H2O",
                    wrongAnswers = listOf("CO2", "O2", "H2O2")
                )
            )
            questions.add(
                Question(
                    category = "Science",
                    text = "Qui a développé la théorie de la relativité ?",
                    correctAnswer = "Albert Einstein",
                    wrongAnswers = listOf("Isaac Newton", "Galileo Galilei", "Niels Bohr")
                )
            )
            questions.add(
                Question(
                    category = "Science",
                    text = "Quel est l'élément le plus abondant dans l'univers ?",
                    correctAnswer = "Hydrogène",
                    wrongAnswers = listOf("Azote", "Oxygène", "Hélium")
                )
            )
            questions.add(
                Question(
                    category = "Science",
                    text = "Quelle est la planète la plus proche du Soleil ?",
                    correctAnswer = "Mercure",
                    wrongAnswers = listOf("Vénus", "Terre", "Mars")
                )
            )
            questions.add(
                Question(
                    category = "Science",
                    text = "Quelle est la vitesse de la lumière dans le vide ?",
                    correctAnswer = "299,792,458 m/s",
                    wrongAnswers = listOf("100,000 m/s", "1,000,000 m/s", "500,000,000 m/s")
                )
            )
            questions.add(
                Question(
                    category = "Science",
                    text = "Combien d'os a un adulte humain typique ?",
                    correctAnswer = "206",
                    wrongAnswers = listOf("198", "214", "222")
                )
            )
            questions.add(
                Question(
                    category = "Science",
                    text = "Quel est le processus par lequel les plantes produisent de la nourriture ?",
                    correctAnswer = "Photosynthèse",
                    wrongAnswers = listOf("Respiration", "Transpiration", "Digestion")
                )
            )
            questions.add(
                Question(
                    category = "Science",
                    text = "Quel est le plus grand organe du corps humain ?",
                    correctAnswer = "La peau",
                    wrongAnswers = listOf("Le foie", "Le cerveau", "Les poumons")
                )
            )
            questions.add(
                Question(
                    category = "Science",
                    text = "Quel est le plus petit os du corps humain ?",
                    correctAnswer = "L'étrier",
                    wrongAnswers = listOf("Le marteau", "L'enclume", "Le stapes")
                )
            )
            questions.add(
                Question(
                    category = "Science",
                    text = "Quel est le gaz le plus abondant dans l'atmosphère terrestre ?",
                    correctAnswer = "Azote",
                    wrongAnswers = listOf("Oxygène", "Argon", "Dioxyde de carbone")
                )
            )

            // Histoire
            questions.add(
                Question(
                    category = "Histoire",
                    text = "Quel événement a déclenché la Première Guerre mondiale ?",
                    correctAnswer = "L'assassinat de l'archiduc François-Ferdinand",
                    wrongAnswers = listOf(
                        "La révolution russe",
                        "La guerre civile espagnole",
                        "La bataille de Verdun"
                    )
                )
            )
            questions.add(
                Question(
                    category = "Histoire",
                    text = "Qui était le premier président des États-Unis ?",
                    correctAnswer = "George Washington",
                    wrongAnswers = listOf("Thomas Jefferson", "Abraham Lincoln", "John Adams")
                )
            )
            questions.add(
                Question(
                    category = "Histoire",
                    text = "En quelle année a été signée la Déclaration d'indépendance des États-Unis ?",
                    correctAnswer = "1776",
                    wrongAnswers = listOf("1789", "1799", "1766")
                )
            )
            questions.add(
                Question(
                    category = "Histoire",
                    text = "Quel événement a marqué la fin de la Seconde Guerre mondiale en Europe ?",
                    correctAnswer = "La capitulation de l'Allemagne nazie",
                    wrongAnswers = listOf(
                        "Le débarquement de Normandie",
                        "La bataille de Stalingrad",
                        "L'attaque de Pearl Harbor"
                    )
                )
            )
            questions.add(
                Question(
                    category = "Histoire",
                    text = "Qui était le pharaon qui a construit la Grande Pyramide de Gizeh ?",
                    correctAnswer = "Khéops",
                    wrongAnswers = listOf("Ramsès II", "Hatchepsout", "Toutânkhamon")
                )
            )
            questions.add(
                Question(
                    category = "Histoire",
                    text = "Quelle ville a été la capitale de l'Empire romain d'Occident ?",
                    correctAnswer = "Rome",
                    wrongAnswers = listOf("Alexandrie", "Constantinople", "Athènes")
                )
            )
            questions.add(
                Question(
                    category = "Histoire",
                    text = "Quel empire a été dirigé par Gengis Khan ?",
                    correctAnswer = "L'Empire mongol",
                    wrongAnswers = listOf(
                        "L'Empire romain",
                        "L'Empire ottoman",
                        "L'Empire carolingien"
                    )
                )
            )
            questions.add(
                Question(
                    category = "Histoire",
                    text = "Quel événement a provoqué le début de la Révolution française ?",
                    correctAnswer = "La prise de la Bastille",
                    wrongAnswers = listOf(
                        "L'exécution de Louis XVI",
                        "Le serment du Jeu de paume",
                        "La fuite à Varennes"
                    )
                )
            )
            questions.add(
                Question(
                    category = "Histoire",
                    text = "Qui a été le premier empereur romain ?",
                    correctAnswer = "Auguste",
                    wrongAnswers = listOf("Jules César", "Néron", "Trajan")
                )
            )
            questions.add(
                Question(
                    category = "Histoire",
                    text = "En quelle année la guerre de Sécession aux États-Unis a-t-elle pris fin ?",
                    correctAnswer = "1865",
                    wrongAnswers = listOf("1850", "1875", "1900")
                )
            )

            // Géographie
            questions.add(
                Question(
                    category = "Géographie",
                    text = "Quelle est la capitale du Japon ?",
                    correctAnswer = "Tokyo",
                    wrongAnswers = listOf("Osaka", "Kyoto", "Nagoya")
                )
            )
            questions.add(
                Question(
                    category = "Géographie",
                    text = "Quel est le plus grand désert du monde ?",
                    correctAnswer = "Le Sahara",
                    wrongAnswers = listOf(
                        "Le désert de Gobi",
                        "Le désert de Kalahari",
                        "Le désert d'Atacama"
                    )
                )
            )
            questions.add(
                Question(
                    category = "Géographie",
                    text = "Quelle est la plus grande île du monde ?",
                    correctAnswer = "Groenland",
                    wrongAnswers = listOf("Australie", "Madagascar", "Borneo")
                )
            )
            questions.add(
                Question(
                    category = "Géographie",
                    text = "Où se trouve le plus haut sommet du monde, le mont Everest ?",
                    correctAnswer = "Népal",
                    wrongAnswers = listOf("Inde", "Chine", "Bhoutan")
                )
            )
            questions.add(
                Question(
                    category = "Géographie",
                    text = "Quel est le pays le plus peuplé du monde ?",
                    correctAnswer = "Chine",
                    wrongAnswers = listOf("Inde", "États-Unis", "Brésil")
                )
            )
            questions.add(
                Question(
                    category = "Géographie",
                    text = "Quel est le plus long fleuve du monde ?",
                    correctAnswer = "L'Amazone",
                    wrongAnswers = listOf("Le Nil", "Le Yangzi Jiang", "Le Mississippi")
                )
            )
            questions.add(
                Question(
                    category = "Géographie",
                    text = "Quel est le plus grand océan du monde ?",
                    correctAnswer = "L'océan Pacifique",
                    wrongAnswers = listOf(
                        "L'océan Atlantique",
                        "L'océan Indien",
                        "L'océan Arctique"
                    )
                )
            )
            questions.add(
                Question(
                    category = "Géographie",
                    text = "Quel est le plus grand lac d'eau douce du monde ?",
                    correctAnswer = "Le lac Supérieur",
                    wrongAnswers = listOf("Le lac Victoria", "Le lac Baïkal", "Le lac Tanganyika")
                )
            )
            questions.add(
                Question(
                    category = "Géographie",
                    text = "Quel pays est souvent appelé 'le pays du matin calme' ?",
                    correctAnswer = "Corée du Sud",
                    wrongAnswers = listOf("Japon", "Chine", "Vietnam")
                )
            )
            questions.add(
                Question(
                    category = "Géographie",
                    text = "Quel est le plus grand archipel du monde ?",
                    correctAnswer = "L'archipel d'Indonésie",
                    wrongAnswers = listOf(
                        "L'archipel des Philippines",
                        "L'archipel d'Hawaï",
                        "L'archipel des Maldives"
                    )
                )
            )
            questions.add(
                Question(
                    category = "Géographie",
                    text = "Quel est le plus grand pays d'Amérique du Sud ?",
                    correctAnswer = "Le Brésil",
                    wrongAnswers = listOf("L'Argentine", "La Colombie", "Le Pérou")
                )
            )
            questions.add(
                Question(
                    category = "Géographie",
                    text = "Quel est le plus grand pays d'Afrique par superficie ?",
                    correctAnswer = "L'Algérie",
                    wrongAnswers = listOf(
                        "Le Soudan",
                        "La République Démocratique du Congo",
                        "L'Égypte"
                    )
                )
            )

            // Technologie
            questions.add(
                Question(
                    category = "Technologie",
                    text = "Qui est le fondateur de Microsoft ?",
                    correctAnswer = "Bill Gates",
                    wrongAnswers = listOf("Steve Jobs", "Elon Musk", "Mark Zuckerberg")
                )
            )
            questions.add(
                Question(
                    category = "Technologie",
                    text = "Quel est le langage de programmation principal pour le développement Android ?",
                    correctAnswer = "Kotlin",
                    wrongAnswers = listOf("Java", "C++", "Python")
                )
            )
            questions.add(
                Question(
                    category = "Technologie",
                    text = "Quel est le plus grand réseau social au monde ?",
                    correctAnswer = "Facebook",
                    wrongAnswers = listOf("Twitter", "Instagram", "LinkedIn")
                )
            )
            questions.add(
                Question(
                    category = "Technologie",
                    text = "Quelle entreprise a développé le premier microprocesseur commercialisé ?",
                    correctAnswer = "Intel",
                    wrongAnswers = listOf("AMD", "Apple", "IBM")
                )
            )
            questions.add(
                Question(
                    category = "Technologie",
                    text = "Quel est le nom du premier satellite artificiel placé en orbite autour de la Terre ?",
                    correctAnswer = "Spoutnik 1",
                    wrongAnswers = listOf("Explorer 1", "Vostok 1", "Apollo 11")
                )
            )
            questions.add(
                Question(
                    category = "Technologie",
                    text = "Qui a inventé le World Wide Web (WWW) ?",
                    correctAnswer = "Tim Berners-Lee",
                    wrongAnswers = listOf("Bill Gates", "Steve Jobs", "Larry Page")
                )
            )
            questions.add(
                Question(
                    category = "Technologie",
                    text = "Quelle est la capacité maximale d'un DVD standard (4,7 Go) ?",
                    correctAnswer = "4,7 gigaoctets",
                    wrongAnswers = listOf("1 gigaoctet", "10 gigaoctets", "100 mégaoctets")
                )
            )
            questions.add(
                Question(
                    category = "Technologie",
                    text = "Quelle est la plus grande société de commerce électronique au monde ?",
                    correctAnswer = "Amazon",
                    wrongAnswers = listOf("Alibaba", "eBay", "Walmart")
                )
            )
            questions.add(
                Question(
                    category = "Technologie",
                    text = "Quelle est la première console de jeux vidéo commerciale ?",
                    correctAnswer = "Magnavox Odyssey",
                    wrongAnswers = listOf(
                        "Atari 2600",
                        "Nintendo Entertainment System (NES)",
                        "Sega Master System"
                    )
                )
            )

            // Divertissement
            questions.add(
                Question(
                    category = "Divertissement",
                    text = "Qui joue le rôle principal dans le film 'Titanic' ?",
                    correctAnswer = "Leonardo DiCaprio",
                    wrongAnswers = listOf("Brad Pitt", "Johnny Depp", "Tom Cruise")
                )
            )
            questions.add(
                Question(
                    category = "Divertissement",
                    text = "Quel groupe a sorti l'album 'Abbey Road' ?",
                    correctAnswer = "The Beatles",
                    wrongAnswers = listOf("The Rolling Stones", "Pink Floyd", "Led Zeppelin")
                )
            )
            questions.add(
                Question(
                    category = "Divertissement",
                    text = "Quel acteur a joué James Bond dans 'Skyfall' ?",
                    correctAnswer = "Daniel Craig",
                    wrongAnswers = listOf("Pierce Brosnan", "Sean Connery", "Roger Moore")
                )
            )
            questions.add(
                Question(
                    category = "Divertissement",
                    text = "Quel est le nom du personnage principal dans 'Breaking Bad' ?",
                    correctAnswer = "Walter White",
                    wrongAnswers = listOf("Jesse Pinkman", "Saul Goodman", "Skyler White")
                )
            )
            questions.add(
                Question(
                    category = "Divertissement",
                    text = "Quelle actrice a joué Katniss Everdeen dans 'The Hunger Games' ?",
                    correctAnswer = "Jennifer Lawrence",
                    wrongAnswers = listOf("Emma Watson", "Scarlett Johansson", "Angelina Jolie")
                )
            )
            questions.add(
                Question(
                    category = "Divertissement",
                    text = "Qui a écrit 'Le Seigneur des Anneaux' ?",
                    correctAnswer = "J.R.R. Tolkien",
                    wrongAnswers = listOf("George R.R. Martin", "C.S. Lewis", "J.K. Rowling")
                )
            )
            questions.add(
                Question(
                    category = "Divertissement",
                    text = "Quel est le film d'animation le plus rentable de tous les temps ?",
                    correctAnswer = "Le Roi Lion (1994)",
                    wrongAnswers = listOf("Toy Story", "La Reine des Neiges", "Shrek 2")
                )
            )
            questions.add(
                Question(
                    category = "Divertissement",
                    text = "Quel réalisateur a dirigé 'Pulp Fiction' ?",
                    correctAnswer = "Quentin Tarantino",
                    wrongAnswers = listOf(
                        "Martin Scorsese",
                        "Steven Spielberg",
                        "Christopher Nolan"
                    )
                )
            )
            questions.add(
                Question(
                    category = "Divertissement",
                    text = "Quelle série télévisée est basée sur les romans de George R.R. Martin ?",
                    correctAnswer = "Game of Thrones",
                    wrongAnswers = listOf("The Walking Dead", "Breaking Bad", "Stranger Things")
                )
            )
            questions.add(
                Question(
                    category = "Divertissement",
                    text = "Qui a créé le personnage de Sherlock Holmes ?",
                    correctAnswer = "Arthur Conan Doyle",
                    wrongAnswers = listOf("Agatha Christie", "Edgar Allan Poe", "Raymond Chandler")
                )
            )

            // Animaux
            questions.add(
                Question(
                    category = "Animaux",
                    text = "Quel est le plus grand mammifère du monde ?",
                    correctAnswer = "La baleine bleue",
                    wrongAnswers = listOf("L'éléphant", "Le rhinocéros", "L'hippopotame")
                )
            )
            questions.add(
                Question(
                    category = "Animaux",
                    text = "Combien de pattes ont les araignées ?",
                    correctAnswer = "8",
                    wrongAnswers = listOf("6", "10", "12")
                )
            )
            questions.add(
                Question(
                    category = "Animaux",
                    text = "Quel est le plus grand oiseau du monde ?",
                    correctAnswer = "L'autruche",
                    wrongAnswers = listOf("L'aigle royal", "Le condor des Andes", "Le casoar")
                )
            )
            questions.add(
                Question(
                    category = "Animaux",
                    text = "Quel animal est souvent appelé 'le roi de la jungle' ?",
                    correctAnswer = "Le lion",
                    wrongAnswers = listOf("Le tigre", "Le léopard", "L'ours")
                )
            )
            questions.add(
                Question(
                    category = "Animaux",
                    text = "Quel est l'animal terrestre le plus rapide ?",
                    correctAnswer = "La guépard",
                    wrongAnswers = listOf("Le lion", "L'antilope", "Le léopard")
                )
            )
            questions.add(
                Question(
                    category = "Animaux",
                    text = "Quel est le plus grand reptile du monde ?",
                    correctAnswer = "Le crocodile marin",
                    wrongAnswers = listOf(
                        "Le python réticulé",
                        "Le varan de Komodo",
                        "La tortue luth"
                    )
                )
            )
            questions.add(
                Question(
                    category = "Animaux",
                    text = "Quel est le plus grand insecte vivant au monde ?",
                    correctAnswer = "Le phasme bâton",
                    wrongAnswers = listOf(
                        "La mante religieuse",
                        "Le scarabée Goliath",
                        "La libellule"
                    )
                )
            )
            questions.add(
                Question(
                    category = "Animaux",
                    text = "Quel est l'animal le plus rapide sous l'eau ?",
                    correctAnswer = "Le dauphin",
                    wrongAnswers = listOf("L'orque", "Le requin", "La baleine")
                )
            )
            questions.add(
                Question(
                    category = "Animaux",
                    text = "Quel est le plus grand animal terrestre ?",
                    correctAnswer = "L'éléphant d'Afrique",
                    wrongAnswers = listOf("Le girafe", "L'hippopotame", "Le rhinocéros")
                )
            )
            questions.add(
                Question(
                    category = "Animaux",
                    text = "Quel est le plus grand prédateur de l'océan ?",
                    correctAnswer = "Le grand requin blanc",
                    wrongAnswers = listOf("L'orque", "Le cachalot", "Le calmar géant")
                )
            )
            questions.add(
                Question(
                    category = "Animaux",
                    text = "Quel est le nom du plus grand poisson du monde ?",
                    correctAnswer = "Le requin baleine",
                    wrongAnswers = listOf(
                        "Le grand requin blanc",
                        "Le mérou géant",
                        "La raie manta"
                    )
                )
            )

            // Insérer toutes les questions dans la base de données
            questionDao.insertAll(questions)
            Log.d("QuizDatabase", "Inserted questions")
        }
    }
}
