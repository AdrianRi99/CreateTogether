package com.example.createtogether.utility

import android.graphics.Color
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import com.example.createtogether.ressources.diff_match_patch


class DiffUtil {

//    val red = Color.parseColor("#FFC0CB") // pastellrosa Farbcode
//    val yellow = Color.parseColor("#FFFACD") // pastellgelb Farbcode
//    val blau = Color.parseColor("#7EC8E3") // pastellblau Farbcode

    val red = Color.parseColor("#B25B6A") // pastellrosa Farbcode
    val yellow = Color.parseColor("#FFFACD") // pastellgelb Farbcode
    val blau = Color.parseColor("#408199") // pastellblau Farbcode

    fun generateHighlightedTextWithAdditionsAndRemovals(originalText: String, modifiedText: String): SpannableString {
        val dmp = diff_match_patch()
        val diffs = dmp.diff_main(originalText, modifiedText)
        dmp.diff_cleanupSemantic(diffs)

        val spannableStringBuilder = SpannableStringBuilder()

        var originalPosition = 0
        var modifiedPosition = 0

        for (diff in diffs) {
            when (diff.operation) {
                diff_match_patch.Operation.DELETE -> {
                    spannableStringBuilder.append(SpannableString(originalText.substring(originalPosition, originalPosition + diff.text.length)).apply {
                        setSpan(
                            BackgroundColorSpan(red),
                            0,
                            length,
                            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    })
                    originalPosition += diff.text.length
                }

                diff_match_patch.Operation.INSERT -> {
                    spannableStringBuilder.append(SpannableString(modifiedText.substring(modifiedPosition, modifiedPosition + diff.text.length)).apply {
                        setSpan(
                            BackgroundColorSpan(blau),
                            0,
                            length,
                            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    })
                    modifiedPosition += diff.text.length
                }

                diff_match_patch.Operation.EQUAL -> {
                    spannableStringBuilder.append(originalText.substring(originalPosition, originalPosition + diff.text.length))
                    originalPosition += diff.text.length
                    modifiedPosition += diff.text.length
                }
                else -> {
                }
            }
        }

        return SpannableString(spannableStringBuilder)
    }

    fun generateHighlightedTextWithOnlyAdditions(originalText: String, modifiedText: String): SpannableString {
        val dmp = diff_match_patch()
        val diffs = dmp.diff_main(originalText, modifiedText)
        dmp.diff_cleanupSemantic(diffs)

        val spannableStringBuilder = SpannableStringBuilder(modifiedText)

        var originalPosition = 0
        var modifiedPosition = 0

        for (diff in diffs) {
            when (diff.operation) {
                diff_match_patch.Operation.DELETE -> {
                }

                diff_match_patch.Operation.INSERT -> {
                    spannableStringBuilder.setSpan(
                        BackgroundColorSpan(blau),
                        modifiedPosition,
                        modifiedPosition + diff.text.length,
                        SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    modifiedPosition += diff.text.length
                }

                diff_match_patch.Operation.EQUAL -> {
                    originalPosition += diff.text.length
                    modifiedPosition += diff.text.length
                }
                else -> {}
            }
        }

        return SpannableString(spannableStringBuilder)
    }

    //LongestCommonSubsequence um evtl noch effektiver arbeiten zu können

//    fun highlightChanges(originalText: String, modifiedText: String): SpannableString {
//        val lcs = LongestCommonSubsequence(originalText, modifiedText)
//        val commonSubsequence = lcs.getLCS()
//
//        val spannableStringBuilder = SpannableStringBuilder()
//
//        var originalPosition = 0
//        var modifiedPosition = 0
//
//        for (subsequence in commonSubsequence) {
//            while (originalText[originalPosition] != subsequence[0]) {
//                spannableStringBuilder.append(SpannableString(originalText[originalPosition].toString()).apply {
//                    setSpan(
//                        BackgroundColorSpan(Color.RED),
//                        0,
//                        length,
//                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
//                    )
//                })
//                originalPosition++
//            }
//
//            while (modifiedText[modifiedPosition] != subsequence[0]) {
//                spannableStringBuilder.append(SpannableString(modifiedText[modifiedPosition].toString()).apply {
//                    setSpan(
//                        BackgroundColorSpan(Color.YELLOW),
//                        0,
//                        length,
//                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
//                    )
//                })
//                modifiedPosition++
//            }
//
//            spannableStringBuilder.append(SpannableString(subsequence).apply {
//                // Keine Hervorhebung für gemeinsame Sequenzen
//            })
//
//            originalPosition += subsequence.length
//            modifiedPosition += subsequence.length
//        }
//
//        while (originalPosition < originalText.length) {
//            spannableStringBuilder.append(SpannableString(originalText[originalPosition].toString()).apply {
//                setSpan(
//                    BackgroundColorSpan(Color.RED),
//                    0,
//                    length,
//                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
//                )
//            })
//            originalPosition++
//        }
//
//        while (modifiedPosition < modifiedText.length) {
//            spannableStringBuilder.append(SpannableString(modifiedText[modifiedPosition].toString()).apply {
//                setSpan(
//                    BackgroundColorSpan(Color.YELLOW),
//                    0,
//                    length,
//                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
//                )
//            })
//            modifiedPosition++
//        }
//
//        return SpannableString(spannableStringBuilder)
//    }

}