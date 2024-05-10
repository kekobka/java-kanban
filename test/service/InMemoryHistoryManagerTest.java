package service;

public class InMemoryHistoryManagerTest extends HistoryManagerTest<InMemoryHistoryManager> {

    @Override
    public InMemoryHistoryManager createManager() {
        return new InMemoryHistoryManager();
    }
}
