public class PaymentStatusStrategyWrapper {
    public static String getPaymentStatus(String idMetodoPagamento) {
        if (idMetodoPagamento == 'Carta_di_credito') {
            return 'credito_soggiorno';
        } else if (idMetodoPagamento == 'Carta_di_debito_Bancomat') {
            return 'pagamento_effettuato';
        } else if (idMetodoPagamento == 'Bonifico') {
            return 'in_verifica';
        } else {
            throw new IllegalArgumentException('Metodo di pagamento non supportato: ' + idMetodoPagamento);
        }
    }
}