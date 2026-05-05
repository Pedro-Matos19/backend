package org.bibliotecaviva.backend.application.dtos.response;

public record AdminDashboardResponseDTO(
        Long totalPosts,
        Long totalComments,
        Long totalBookClubReviews,
        Long totalUsers,
        Long pendingUsers
) {
}
