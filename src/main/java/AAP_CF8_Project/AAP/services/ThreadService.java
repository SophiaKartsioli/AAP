package AAP_CF8_Project.AAP.services;


import AAP_CF8_Project.AAP.domain.ForumThread;

public interface ThreadService {
    Iterable<ForumThread> findAll();
}
