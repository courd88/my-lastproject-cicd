package project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

@RestController
public class TranslateController {
	
	
	@GetMapping("/translate")
	public String translate(@RequestParam(value="message") String text, @RequestParam(value="lang") String lang) {
		Translate translate = TranslateOptions.getDefaultInstance().getService();

        Translation translation = translate.translate(text, Translate.TranslateOption.targetLanguage(lang));
        return translation.getTranslatedText();
	}
}
