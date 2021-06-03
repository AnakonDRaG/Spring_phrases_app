package com.SGW.Phrases.converters

import com.SGW.Phrases.converters.models.HtmlOption

class HtmlConverter {
    static HtmlOption convertToOption(String id, String text) {
        return [id:id, text:text]
    }
}
