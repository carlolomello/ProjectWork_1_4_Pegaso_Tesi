public with sharing class OverlappingValidatorFactory {
    public static OverlappingValidatorInterface getValidator(String type) {
        if (type == 'Prenotazione') {
            return new PrenotazioneOverlappingValidator();
        } else if (type == 'PrenotazioneServizio'){
            return new PrenotazioneServizioOverlappingValidator();
        }
        return null;
    }
}