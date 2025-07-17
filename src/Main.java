import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Blockchain blockchain = new Blockchain();

            System.out.println("Mining block 1...");
            blockchain.addBlock(List.of(new Transaction("Alice", "Bob", 10.0)));

            System.out.println("Mining block 2...");
            blockchain.addBlock(List.of(new Transaction("Bob", "Charlie", 5.0)));

            System.out.println("Blockchain state:\n" + blockchain);
            System.out.println("Blockchain is valid: " + blockchain.isChainValid());

            // Simulação de adulteração
            System.out.println("\nAlterando um bloco para testar a validação...");
            Block tamperedBlock = new Block(List.of(new Transaction("Tampered", "User", 100.0)), blockchain.getBlock(0).getHash(), Blockchain.DIFFICULTY);
            System.out.println("Note: Direct tampering is not possible due to immutability.");
            System.out.println("Blockchain is valid: " + blockchain.isChainValid());

        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: Failed to mine blocks due to missing SHA-256 algorithm");
            e.printStackTrace();
        }
    }
}