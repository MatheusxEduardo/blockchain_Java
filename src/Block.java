import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Block {
    private final String hash;
    private final String previousHash;
    private final List<Transaction> transactions;
    private final long timeStamp;
    private final int nonce;

    public Block(List<Transaction> transactions, String previousHash, int difficulty) throws NoSuchAlgorithmException {
        this.transactions = new ArrayList<>(transactions);
        this.previousHash = previousHash;
        this.timeStamp = System.currentTimeMillis();
        this.nonce = mineNonce(difficulty); // Calcula o nonce durante a construção
        this.hash = calculateHash();
    }

    public Block(String genesisData, String previousHash, int difficulty) throws NoSuchAlgorithmException {
        this.transactions = List.of(new Transaction(genesisData, "", 0.0)); // Transação fictícia para gênese
        this.previousHash = previousHash;
        this.timeStamp = System.currentTimeMillis();
        this.nonce = mineNonce(difficulty);
        this.hash = calculateHash();
    }

    private int mineNonce(int difficulty) throws NoSuchAlgorithmException {
        String target = "0".repeat(difficulty);
        int newNonce = 0;
        String newHash;
        do {
            StringBuilder dataToHash = new StringBuilder();
            dataToHash.append(previousHash)
                    .append(timeStamp)
                    .append(newNonce)
                    .append(transactions.toString());
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(dataToHash.toString().getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (byte b : bytes) {
                builder.append(String.format("%02x", b));
            }
            newHash = builder.toString();
            newNonce++;
        } while (!newHash.startsWith(target));
        System.out.println("Block mined! Hash: " + newHash + ", Nonce: " + (newNonce - 1));
        return newNonce - 1;
    }

    public String calculateHash() throws NoSuchAlgorithmException {
        StringBuilder dataToHash = new StringBuilder();
        dataToHash.append(previousHash)
                .append(timeStamp)
                .append(nonce)
                .append(transactions.toString());
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = digest.digest(dataToHash.toString().getBytes(StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    // Getters
    public String getHash() { return hash; }
    public String getPreviousHash() { return previousHash; }
    public List<Transaction> getTransactions() { return new ArrayList<>(transactions); }
    public long getTimeStamp() { return timeStamp; }
    public int getNonce() { return nonce; }
}