package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.Thread;
import AAP_CF8_Project.AAP.repository.ThreadRepository;


public class ThreadServiceImpl implements ThreadService {

    private final ThreadRepository threadRepository;

    public ThreadServiceImpl(ThreadRepository threadRepositories) {this.threadRepository = threadRepositories;}

    @Override
    public Iterable<Thread> findAll() {return threadRepository.findAll();}
}
