package AAP_CF8_Project.AAP.services;

import AAP_CF8_Project.AAP.domain.ForumThread;
import AAP_CF8_Project.AAP.repository.ThreadRepository;


public class ThreadServiceImpl implements ThreadService {

    private final ThreadRepository threadRepository;

    public ThreadServiceImpl(ThreadRepository threadRepositories) {this.threadRepository = threadRepositories;}

    @Override
    public Iterable<ForumThread> findAll() {return threadRepository.findAll();}
}
