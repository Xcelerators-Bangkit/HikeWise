package com.example.hikewise.data.question

import android.content.Context
import android.util.Log

class QuestionRepository {
    private val answersMap = mutableMapOf<Int, String>()

    companion object {
        @Volatile
        private var instance: QuestionRepository? = null

        fun getInstance(): QuestionRepository {
            return instance ?: synchronized(this) {
                instance ?: QuestionRepository().also { instance = it }
            }
        }
    }

//    companion object {
//        @Volatile
//        private var instance: QuestionRepository? = null
//
//        fun getInstance(): QuestionRepository {
//            return instance ?: synchronized(this) {
//                if (instance == null) {
//                    instance = QuestionRepository()
//                }
//                return instance as QuestionRepository
//            }
//        }
//
//    }

    fun getQuestions(): List<Question> {
        return QuestionsList.questions
    }

    fun saveAnswer(answer: Option) {
        answersMap[answer.questionId] = answer.option
        Log.d("Save Answer", "Answer saved for Question ${answer.questionId}: ${answer.option}")
    }

    fun resetAnswers() {
        answersMap.clear()
        Log.d("Reset Answers", "Answers reset")
    }

    private fun getAnswerByQuestion(questionId: Int): String? {
        Log.d("Get Question", "Answer for Question $questionId: ${answersMap[questionId]}")
        return answersMap[questionId]
    }

    fun getResultMessage(): String {
        val answerForQuestion1 = getAnswerByQuestion(1)
        val answerForQuestion2 = getAnswerByQuestion(2)
        val answerForQuestion3 = getAnswerByQuestion(3)
        val answerForQuestion4 = getAnswerByQuestion(4)
        val answerForQuestion5 = getAnswerByQuestion(5)


        return when {
            answerForQuestion1 == "Yes" && answerForQuestion2 == "No" && answerForQuestion3 == "No"
                    && answerForQuestion4 == "No"  && answerForQuestion5 == "No" -> {
                "Lebih baik kamu berkonsultasi dengan dokter terlebih dahulu ya! " +
                        "Jika kamu mendapat lampu hijau dari dokter konsultasi, jangan lupa untuk " +
                        "selalu mengikuti saran dokter, membawa obat asmamu yang sudah di resepkan dokter, " +
                        "alat komunikasi darurat, air bersih untuk minum, dan teruslah berkomunikasi " +
                        "dengan tim atau rombonganmu mengenai kondisimu. Stay healthy :)"
            }
            answerForQuestion1 == "No" && answerForQuestion2 == "Yes" && answerForQuestion3 == "No"
                    && answerForQuestion4 == "No" && answerForQuestion5 == "No" -> {
                "Konsultasilah dahulu dengan dokter untuk mendapat penilaian mengenai cederamu ya! " +
                        "Jangan memaksakan diri jika dokter menyarankan untuk menunda pendakian, namun " +
                        "jika mendapat persetujuan ikutilah saran panduan dokter mengenai sejauh mana " +
                        "kamu dapat melakukan aktivitas fisik demi mencegah risiko cedera yang lebih parah. " +
                        "Jangan lupa membawa obat analgesik, antiflamasi, penyemprot dingin, alat " +
                        "pemotong atau penjepit, air bersih untuk minum, dan alat komunikasi darurat. " +
                        "Stay healthy :)"
            }
            answerForQuestion1 == "No" && answerForQuestion2 == "No" && answerForQuestion3 == "Yes"
                    && answerForQuestion4 == "No" && answerForQuestion5 == "No" -> {
                "Pertimbangkanlah keputusanmu untuk mendaki dengan sangat hati-hati ya! Lebih baik " +
                        "konsultasi terlebih dahulu mengenai kondisimu dengan profesional kesehatan " +
                        "dan ikutilah saran yang direkomendasikan. Dan untuk memastikan keamanan selama " +
                        "pendakian, jangan lupa untuk searching mengenai trek pendakianmu dan selalu " +
                        "membawa spray oksigen, obat antimetik, analgesik, alat komunikasi darurat, dan " +
                        "air bersih untuk minum. Stay healthy :) "
            }
            answerForQuestion1 == "No" && answerForQuestion2 == "No" && answerForQuestion3 == "No"
                    && answerForQuestion4 == "Yes" && answerForQuestion5 == "No" -> {
                "Lebih baik kamu berkonsultasi dengan dokter terlebih dahulu ya! Agar dokter dapat " +
                        "mengevaluasi tingkat keparahan alergimu dan memberikan saran tindakan " +
                        "pencegahan yang sesuai. Jika kamu mendapat lampu hijau dari dokter konsultasi, " +
                        "jangan lupa untuk mempersiapkan pakaian hangat, lapisan pelindung, krim " +
                        "pelembab, obat antialergi, alat komunikasi darurat, air besih untuk minum, " +
                        "dan yang paling penting pahami batasan untuk kondisimu. Stay healthy :)"
            }
            answerForQuestion1 == "No" && answerForQuestion2 == "No" && answerForQuestion3 == "No"
                    && answerForQuestion4 == "No" && answerForQuestion5 == "Yes" -> {
                "Saya sarankan konsultasilah terlebih dahulu dengan dokter mengenai kesehatan " +
                        "jantungmu saat ini, agar dokter dapat melakukan pengawasan untuk kamu ketika " +
                        "melakukan aktivitas fisik yang berat. Dan jangan lupa selalu membawa obat" +
                        "yang sudah di resepkan dokter, alat komunikasi darurat, P3K, dan air bersih " +
                        "untuk minum. Stay healthy :)"
            }
            answerForQuestion1 == "Yes" && answerForQuestion2 == "Yes" && answerForQuestion3 == "No"
                    && answerForQuestion4 == "No" && answerForQuestion5 == "No" -> {
                "Konsultasilah dahulu dengan dokter untuk mendapat penilaian mengenai asma dan " +
                        "cederamu ya! Jangan memaksakan diri jika dokter menyarankan untuk menunda " +
                        "pendakian, namun jika mendapat persetujuan ikutilah saran panduan dokter " +
                        "mengenai sejauh mana kamu dapat melakukan aktivitas fisik demi mencegah " +
                        "risiko kondisimu lebih parah. Jangan lupa selalulah membawa obat asmamu " +
                        "yang sudah di resepkan dokter, obat analgesik, antiflamasi, penyemprot " +
                        "dingin, alat pemotong atau penjepit, air bersih untuk minum, alat " +
                        "komunikasi darurat, dan teruslah berkomunikasi dengan tim atau rombonganmu " +
                        "mengenai kondisimu. Stay healthy :)"
            }
            answerForQuestion1 == "Yes" && answerForQuestion2 == "No" && answerForQuestion3 == "Yes"
                    && answerForQuestion4 == "No" && answerForQuestion5 == "No" -> {
                "Saya sarankan konsultasilah terlebih dahulu dengan profesional kesehatan mengenai " +
                        "asma dan kondisi keseluruhanmu. Ikutilah saran yang direkomendasikan oleh " +
                        "dokter demi memastikan keamanan selama pendakian, jangan lupa untuk " +
                        "searching mengenai trek pendakianmu dan selalu membawa spray oksigen, obat " +
                        "antimetik, analgesik, obat asma yang sudah di resepkan dokter, alat " +
                        "komunikasi darurat, dan air bersih untuk minum. Stay healthy :) "
            }
            answerForQuestion1 == "Yes" && answerForQuestion2 == "No" && answerForQuestion3 == "No"
                    && answerForQuestion4 == "Yes" && answerForQuestion5 == "No" -> {
                "Pertimbangkanlah keputusanmu untuk mendaki dengan sangat hati-hati ya! Lebih baik " +
                        "konsultasi terlebih dahulu mengenai kondisimu dengan profesional kesehatan. " +
                        "Agar dokter dapat mengevaluasi tingkat keparahan alergi dan asma yang kamu " +
                        "derita dan memberikan saran tindakan pencegahan yang sesuai. Jika kamu " +
                        "mendapat lampu hijau dari dokter konsultasi, jangan lupa untuk mempersiapkan " +
                        "obat-obat asma yang sudah diresepkan dokter, pakaian hangat, lapisan pelindung, " +
                        "krim pelembab, obat antialergi, alat komunikasi darurat, air besih untuk minum, " +
                        "dan yang paling penting pahami batasan untuk kondisimu. Stay healthy :)"
            }
            answerForQuestion1 == "Yes" && answerForQuestion2 == "No" && answerForQuestion3 == "No"
                    && answerForQuestion4 == "No" && answerForQuestion5 == "Yes" -> {
                "Konsultasilah dahulu dengan dokter mengenai tingkat keparahan asma dan kesehatan " +
                        "jantungmu agar dokter dapat melakukan pengawasan untuk kamu ketika " +
                        "melakukan aktivitas fisik yang berat. Dan jangan lupa selalu membawa obat "+
                        "jantung dan asma yang sudah di resepkan dokter, inhaler, alat komunikasi " +
                        "darurat, P3K, dan air bersih untuk minum. Stay healthy :)"
            }
            answerForQuestion1 == "No" && answerForQuestion2 == "Yes" && answerForQuestion3 == "Yes"
                    && answerForQuestion4 == "No" && answerForQuestion5 == "No" -> {
                "Saya sarankan konsultasilah terlebih dahulu dengan profesional kesehatan mengenai " +
                        "cedera dan kondisi kesehatan seluruh tubuhmu. Jangan memaksakan diri jika " +
                        "dokter menyarankan untuk menunda pendakian, namun jika mendapat persetujuan " +
                        "ikutilah saran panduan dokter mengenai sejauh mana kamu dapat melakukan " +
                        "aktivitas fisik demi mencegah risiko cedera yang lebih parah. Jangan lupa " +
                        "untuk searching mengenai trek pendakianmu dan selalu membawa obat analgesik, " +
                        "antiflamasi, antimetik, penyemprot dingin, spray oksigen, alat pemotong " +
                        "atau penjepit, air bersih untuk minum, dan alat komunikasi darurat. Stay " +
                        "healthy :)"
            }
            answerForQuestion1 == "No" && answerForQuestion2 == "Yes" && answerForQuestion3 == "No"
                    && answerForQuestion4 == "Yes" && answerForQuestion5 == "No" -> {
                "Pertimbangkanlah keputusanmu untuk mendaki dengan sangat hati-hati ya! Lebih baik " +
                        "konsultasi terlebih dahulu mengenai cedera dan tingkat keparahan alergimu. " +
                        "Agar dokter dapat mengevaluasi tingkat keparahan cedera dan alergimu, juga" +
                        "dokter dapat memberikanm saran tindakan pencegahan yang sesuai. Jika kamu " +
                        "mendapat lampu hijau dari dokter konsultasi, jangan lupa untuk mempersiapkan " +
                        "pakaian hangat, lapisan pelindung, krim pelembap, obat analgesik, antiflamasi, " +
                        "antialergi, penyemprot dingin, alat pemotong atau penjepit, alat komunikasi " +
                        "darurat, air bersih untuk minum, dan yang paling penting pahami batasan " +
                        "untuk kondisimu. Stay healthy :)"
            }
            answerForQuestion1 == "No" && answerForQuestion2 == "Yes" && answerForQuestion3 == "No"
                    && answerForQuestion4 == "No" && answerForQuestion5 == "Yes" -> {
                "Lebih baik kamu berkonsultasi dengan dokter terlebih dahulu ya! Jika kamu mendapat " +
                        "lampu hijau dari dokter konsultasi, ikutilah saran panduan dokter mengenai " +
                        "sejauh mana kamu dapat melakukan aktivitas fisik demi mencegah risiko cedera " +
                        "yang lebih parah. Dan jangan lupa selalu membawa obat yang sudah di resepkan " +
                        "dokter, alat komunikasi darurat, P3K, obat analgesik, antiflamasi, penyemprot " +
                        "dingin, alat pemotong atau penjepit,dan air bersih untuk minum. Stay healthy :)"
            }
            answerForQuestion1 == "No" && answerForQuestion2 == "No" && answerForQuestion3 == "Yes"
                    && answerForQuestion4 == "Yes" && answerForQuestion5 == "No" -> {
                "Pertimbangkanlah keputusanmu untuk mendaki dengan sangat hati-hati ya! Lebih baik " +
                        "konsultasi lebih dahulu mengenai tingkat alergi dan kondisi menyeluruhmu " +
                        "dengan profesional kesehatan dan ikutilah saran yang diterkomendasikan. " +
                        "Dan untuk memastikan keamanan selama pendakian, jangan lupa untuk searching " +
                        "mengenai trek pendakianmu dan selalu membawa spray oksigen, obat antimetik, " +
                        "analgesik, antialergi, pakaian hangat, lapisan pelindung, krim pelembap, " +
                        "alat komunikasi darurat, air bersih untuk minum, dan yang paling penting " +
                        "pahami batasan untuk kondisimu. Stay healthy"
            }
            answerForQuestion1 == "No" && answerForQuestion2 == "No" && answerForQuestion3 == "Yes"
                    && answerForQuestion4 == "No" && answerForQuestion5 == "Yes" -> {
                "Lebih baik kamu berkonsultasi dengan dokter terlebih dahulu ya!  Jangan memaksakan " +
                        "diri jika dokter menyarankan untuk menunda pendakian, namun jika mendapat " +
                        "persetujuan ikutilah saran panduan dokter mengenai sejauh mana kamu dapat " +
                        "melakukan aktivitas fisik demi mencegah risiko kelelahan dan jantung yang " +
                        "lebih parah. Dan untuk memastikan keamanan selama pendakian, jangan lupa " +
                        "untuk searching mengenai trek pendakianmu dan selalu membawa spray oksigen, " +
                        "obat antimetik, analgesik, obat jantung yang sudah di resepkan dokter," +
                        "P3K, alat komunikasi darurat, air bersih untuk minum, dan teruslah berkomunikasi " +
                        "dengan tim atau rombonganmu mengenai kondisimu. Stay healthy :)"
            }
            answerForQuestion1 == "No" && answerForQuestion2 == "No" && answerForQuestion3 == "No"
                    && answerForQuestion4 == "Yes" && answerForQuestion5 == "Yes" -> {
                "Konsultasilah dahulu dengan dokter mengenai tingkat keparahan alergi dan kesehatan " +
                        "jantungmu agar dokter dapat melakukan pengawasan untuk kamu ketika " +
                        "melakukan aktivitas fisik yang berat. Dan jangan lupa selalu membawa obat "+
                        "jantung sudah di resepkan dokter, pakaian hangat, lapisan pelindung, krim " +
                        "pelembap, obat antialergi, alat komunikasi darurat, P3K, air bersih untuk " +
                        "minum, dan teruslah berkomunikasi dengan tim atau rombonganmu mengenai " +
                        "kondisimu. Stay healthy :)"
            }
            answerForQuestion1 == "Yes" && answerForQuestion2 == "Yes" && answerForQuestion3 == "Yes"
                    && answerForQuestion4 == "No" && answerForQuestion5 == "No" -> {
                "Maaf, sepertinya saat ini kamu tidak dalam kondisi fisik yang tidak memungkinkan " +
                        "untuk mendaki. Saya sarankan kamu untuk berkonsultasi dengan profesional " +
                        "kesehatan untuk melihat sejauh mana kondisimu saat ini. Tetap semangat " +
                        "dan stay healthy :)"
            }
            answerForQuestion1 == "No" && answerForQuestion2 == "Yes" && answerForQuestion3 == "Yes"
                    && answerForQuestion4 == "Yes" && answerForQuestion5 == "No" -> {
                "Maaf, sepertinya saat ini kamu tidak dalam kondisi fisik yang tidak memungkinkan " +
                        "untuk mendaki. Saya sarankan kamu untuk berkonsultasi dengan profesional " +
                        "kesehatan untuk melihat sejauh mana kondisimu saat ini. Tetap semangat " +
                        "dan stay healthy :)"
            }
            answerForQuestion1 == "No" && answerForQuestion2 == "No" && answerForQuestion3 == "Yes"
                    && answerForQuestion4 == "Yes" && answerForQuestion5 == "Yes" -> {
                "Maaf, sepertinya saat ini kamu tidak dalam kondisi fisik yang tidak memungkinkan " +
                        "untuk mendaki. Saya sarankan kamu untuk berkonsultasi dengan profesional " +
                        "kesehatan untuk melihat sejauh mana kondisimu saat ini. Tetap semangat " +
                        "dan stay healthy :)"
            }
            answerForQuestion1 == "Yes" && answerForQuestion2 == "No" && answerForQuestion3 == "Yes"
                    && answerForQuestion4 == "Yes" && answerForQuestion5 == "No" -> {
                "Maaf, sepertinya saat ini kamu tidak dalam kondisi fisik yang tidak memungkinkan " +
                        "untuk mendaki. Saya sarankan kamu untuk berkonsultasi dengan profesional " +
                        "kesehatan untuk melihat sejauh mana kondisimu saat ini. Tetap semangat " +
                        "dan stay healthy :)"
            }
            answerForQuestion1 == "Yes" && answerForQuestion2 == "No" && answerForQuestion3 == "No"
                    && answerForQuestion4 == "Yes" && answerForQuestion5 == "Yes" -> {
                "Maaf, sepertinya saat ini kamu tidak dalam kondisi fisik yang tidak memungkinkan " +
                        "untuk mendaki. Saya sarankan kamu untuk berkonsultasi dengan profesional " +
                        "kesehatan untuk melihat sejauh mana kondisimu saat ini. Tetap semangat " +
                        "dan stay healthy :)"
            }
            answerForQuestion1 == "Yes" && answerForQuestion2 == "Yes" && answerForQuestion3 == "No"
                    && answerForQuestion4 == "Yes" && answerForQuestion5 == "No" -> {
                "Maaf, sepertinya saat ini kamu tidak dalam kondisi fisik yang tidak memungkinkan " +
                        "untuk mendaki. Saya sarankan kamu untuk berkonsultasi dengan profesional " +
                        "kesehatan untuk melihat sejauh mana kondisimu saat ini. Tetap semangat " +
                        "dan stay healthy :)"
            }
            answerForQuestion1 == "Yes" && answerForQuestion2 == "Yes" && answerForQuestion3 == "No"
                    && answerForQuestion4 == "No" && answerForQuestion5 == "Yes" -> {
                "Maaf, sepertinya saat ini kamu tidak dalam kondisi fisik yang tidak memungkinkan " +
                        "untuk mendaki. Saya sarankan kamu untuk berkonsultasi dengan profesional " +
                        "kesehatan untuk melihat sejauh mana kondisimu saat ini. Tetap semangat " +
                        "dan stay healthy :)"
            }
            answerForQuestion1 == "No" && answerForQuestion2 == "Yes" && answerForQuestion3 == "Yes"
                    && answerForQuestion4 == "No" && answerForQuestion5 == "Yes" -> {
                "Maaf, sepertinya saat ini kamu tidak dalam kondisi fisik yang tidak memungkinkan " +
                        "untuk mendaki. Saya sarankan kamu untuk berkonsultasi dengan profesional " +
                        "kesehatan untuk melihat sejauh mana kondisimu saat ini. Tetap semangat " +
                        "dan stay healthy :)"
            }
            answerForQuestion1 == "No" && answerForQuestion2 == "Yes" && answerForQuestion3 == "No"
                    && answerForQuestion4 == "Yes" && answerForQuestion5 == "Yes" -> {
                "Maaf, sepertinya saat ini kamu tidak dalam kondisi fisik yang tidak memungkinkan " +
                        "untuk mendaki. Saya sarankan kamu untuk berkonsultasi dengan profesional " +
                        "kesehatan untuk melihat sejauh mana kondisimu saat ini. Tetap semangat " +
                        "dan stay healthy :)"
            }
            answerForQuestion1 == "Yes" && answerForQuestion2 == "No" && answerForQuestion3 == "Yes"
                    && answerForQuestion4 == "No" && answerForQuestion5 == "Yes" -> {
                "Maaf, sepertinya saat ini kamu tidak dalam kondisi fisik yang tidak memungkinkan " +
                        "untuk mendaki. Saya sarankan kamu untuk berkonsultasi dengan profesional " +
                        "kesehatan untuk melihat sejauh mana kondisimu saat ini. Tetap semangat " +
                        "dan stay healthy :)"
            }
            answerForQuestion1 == "Yes" && answerForQuestion2 == "Yes" && answerForQuestion3 == "Yes"
                    && answerForQuestion4 == "Yes" && answerForQuestion5 == "No" -> {
                "Maaf, sepertinya saat ini kamu tidak dalam kondisi fisik yang tidak memungkinkan " +
                        "untuk mendaki. Saya sarankan kamu untuk berkonsultasi dengan profesional " +
                        "kesehatan untuk melihat sejauh mana kondisimu saat ini. Tetap semangat " +
                        "dan stay healthy :)"
            }
            answerForQuestion1 == "No" && answerForQuestion2 == "Yes" && answerForQuestion3 == "Yes"
                    && answerForQuestion4 == "Yes" && answerForQuestion5 == "Yes" -> {
                "Maaf, sepertinya saat ini kamu tidak dalam kondisi fisik yang tidak memungkinkan " +
                        "untuk mendaki. Saya sarankan kamu untuk berkonsultasi dengan profesional " +
                        "kesehatan untuk melihat sejauh mana kondisimu saat ini. Tetap semangat " +
                        "dan stay healthy :)"
            }
            answerForQuestion1 == "Yes" && answerForQuestion2 == "Yes" && answerForQuestion3 == "Yes"
                    && answerForQuestion4 == "No" && answerForQuestion5 == "Yes" -> {
                "Maaf, sepertinya saat ini kamu tidak dalam kondisi fisik yang tidak memungkinkan " +
                        "untuk mendaki. Saya sarankan kamu untuk berkonsultasi dengan profesional " +
                        "kesehatan untuk melihat sejauh mana kondisimu saat ini. Tetap semangat " +
                        "dan stay healthy :)"
            }
            answerForQuestion1 == "Yes" && answerForQuestion2 == "Yes" && answerForQuestion3 == "No"
                    && answerForQuestion4 == "Yes" && answerForQuestion5 == "Yes" -> {
                "Maaf, sepertinya saat ini kamu tidak dalam kondisi fisik yang tidak memungkinkan " +
                        "untuk mendaki. Saya sarankan kamu untuk berkonsultasi dengan profesional " +
                        "kesehatan untuk melihat sejauh mana kondisimu saat ini. Tetap semangat " +
                        "dan stay healthy :)"
            }
            answerForQuestion1 == "Yes" && answerForQuestion2 == "No" && answerForQuestion3 == "Yes"
                    && answerForQuestion4 == "Yes" && answerForQuestion5 == "Yes" -> {
                "Maaf, sepertinya saat ini kamu tidak dalam kondisi fisik yang tidak memungkinkan " +
                        "untuk mendaki. Saya sarankan kamu untuk berkonsultasi dengan profesional " +
                        "kesehatan untuk melihat sejauh mana kondisimu saat ini. Tetap semangat " +
                        "dan stay healthy :)"
            }
            answerForQuestion1 == "Yes" && answerForQuestion2 == "Yes" && answerForQuestion3 == "Yes"
                    && answerForQuestion4 == "Yes" && answerForQuestion5 == "Yes" -> {
                "Maaf, sepertinya saat ini kamu tidak dalam kondisi fisik yang tidak memungkinkan " +
                        "untuk mendaki. Saya sarankan kamu untuk berkonsultasi dengan profesional " +
                        "kesehatan untuk melihat sejauh mana kondisimu saat ini. Tetap semangat " +
                        "dan stay healthy :)"
            }
            answerForQuestion1 == "No" && answerForQuestion2 == "No" && answerForQuestion3 == "No"
                    && answerForQuestion4 == "No" && answerForQuestion5 == "No" -> {
                "Wah kesehatanmu sudah siap untuk mendaki, jaga kesehatan terus ya supaya " +
                        "pendakiannya lancar dan selamat, serta jangan lupa untuk selalu membawa P3K. " +
                        "Stay healthy :)"
            }
            else -> {
                "Maaf kamu belum mengisi form question helath yang tersedia. Harap isi pertanyan " +
                        "tersebut sesuai kondisimu yang sekarang, untuk mendapatkan hasilnya."
            }
        }
    }
}
