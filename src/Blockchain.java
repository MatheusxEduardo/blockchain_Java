import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    public static final int DIFFICULTY = 4;
    private final List<Block> blockchain;

    public Blockchain() {
        blockchain = new ArrayList<>();
        try {
            Block genesisBlock = new Block("Genesis Block", "0", DIFFICULTY);
            blockchain.add(genesisBlock);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to mine genesis block", e);
        }
    }

    public void addBlock(List<Transaction> transactions) throws NoSuchAlgorithmException {
        Block newBlock = new Block(transactions, getLastBlock().getHash(), DIFFICULTY);
        blockchain.add(newBlock);
    }

    public boolean isChainValid() throws NoSuchAlgorithmException {
        String target = "0".repeat(DIFFICULTY);
        for (int i = 1; i < blockchain.size(); i++) {
            Block currentBlock = blockchain.get(i);
            Block previousBlock = blockchain.get(i - 1);

            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                System.out.println("Current hashes not equal at block " + i);
                return false;
            }

            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                System.out.println("Previous hashes not equal at block " + i);
                return false;
            }

            if (!currentBlock.getHash().startsWith(target)) {
                System.out.println("Block " + i + " not mined correctly");
                return false;
            }
        }
        return true;
    }

    // Getters
    public Block getLastBlock() {
        return blockchain.get(blockchain.size() - 1);
    }

    public Block getBlock(int index) {
        return blockchain.get(index);
    }

    public int getChainSize() {
        return blockchain.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Blockchain:\n");
        for (Block block : blockchain) {
            sb.append("Block [hash=").append(block.getHash())
                    .append(", previousHash=").append(block.getPreviousHash())
                    .append(", transactions=").append(block.getTransactions())
                    .append(", timeStamp=").append(block.getTimeStamp())
                    .append(", nonce=").append(block.getNonce())
                    .append("]\n");
        }
        return sb.toString();
    }
}