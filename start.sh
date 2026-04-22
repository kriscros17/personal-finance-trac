#!/bin/bash
echo ""
echo "🪙  Starting Finfolk — Personal Finance Tracker"
echo "────────────────────────────────────────────────"
echo ""
echo "📦  Building backend..."
cd "$(dirname "$0")/backend"
mvn spring-boot:run &
BACKEND_PID=$!
echo ""
echo "✅  Backend starting at http://localhost:8080"
echo "🌐  Open frontend/index.html in your browser"
echo "🗄️   H2 Console at http://localhost:8080/h2-console"
echo ""
echo "Press Ctrl+C to stop the server"
wait $BACKEND_PID
