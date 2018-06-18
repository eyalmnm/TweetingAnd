package com.em_projects.tweetings.utils

class StringUtils {

    companion object {
        /**
         * This method check whether the given String is null or empty (after removing some characters)
         */
        fun isNullOrEmpty(str: String?): Boolean {
            if (str == null) return true
            if (str.isEmpty()) return true
            var tempStr = str;
            tempStr = tempStr.replace("?", "", true)
                    .replace("<", "", true)
                    .replace(">", "", true)
                    .replace("&", "", true)
                    .replace("\"", "", true)
                    .replace("\'", "", true)
                    .replace(";", "", true)
                    .replace("\n", "", true)
                    .replace("\r", "", true)
                    .replace("\t", "", true)
                    .trim()
            if (tempStr.isEmpty()) return true
            return false
        }
    }
}